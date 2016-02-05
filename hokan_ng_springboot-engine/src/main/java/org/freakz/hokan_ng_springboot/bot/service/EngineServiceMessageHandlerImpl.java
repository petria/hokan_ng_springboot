package org.freakz.hokan_ng_springboot.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.command.CommandHandlerService;
import org.freakz.hokan_ng_springboot.bot.command.handlers.Cmd;
import org.freakz.hokan_ng_springboot.bot.enums.HokanModule;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.events.IrcMessageEvent;
import org.freakz.hokan_ng_springboot.bot.jms.JmsEnvelope;
import org.freakz.hokan_ng_springboot.bot.jms.api.JmsSender;
import org.freakz.hokan_ng_springboot.bot.jms.api.JmsServiceMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.util.Date;

/**
 * Created by Petri Airio on 10.2.2015.
 *
 */
@Controller
@Slf4j
public class EngineServiceMessageHandlerImpl implements JmsServiceMessageHandler {

  @Autowired
  private ApplicationContext context;

  @Autowired
  private CommandHandlerService commandHandlerService;

  @Autowired
  private JmsSender jmsSender;

  @Override
  public void handleJmsEnvelope(JmsEnvelope envelope) throws Exception {
    IrcMessageEvent event = (IrcMessageEvent) envelope.getMessageIn().getPayLoadObject("EVENT");
//    log.debug("Handling event: {}", event);
    if (event == null) {
      log.debug("Nothing to do!");
      return;
    }

    CmdHandlerMatches matches = commandHandlerService.getMatchingCommands(event.getMessage());
    if (matches.getMatches().size() > 0) {
      if (matches.getMatches().size() == 1) {
        Cmd handler = matches.getMatches().get(0);
        executeHandler(event, handler);
      } else {
        EngineResponse response = new EngineResponse(event);
        String multiple = matches.getFirstWord() + " multiple matches: ";
        for (Cmd match : matches.getMatches()) {
          multiple += match.getName() + " ";
        }
        response.addResponse(multiple);
        sendReply(response);
      }

    }
/*    Cmd handler = commandHandlerService.getCommandHandler(event.getMessage());

    if (handler != null) {
      EngineResponse response = new EngineResponse(event);
      InternalRequest internalRequest;
      internalRequest = context.getBean(InternalRequest.class);
      try {
        internalRequest.init(event);
        if (!event.isPrivate()) {
          internalRequest.getUserChannel().setLastCommand(event.getMessage());
          internalRequest.getUserChannel().setLastCommandTime(new Date());
          internalRequest.saveUserChannel();
        }
        handler.handleLine(internalRequest, response);
      } catch (Exception e) {
        log.error("Command handler returned exception {}", e);
      }
    }*/
  }

  private void sendReply(EngineResponse response) {
//    log.debug("Sending response: {}", response);
    jmsSender.send(HokanModule.HokanIo.getQueueName(), "ENGINE_RESPONSE", response, false);
  }

  private void executeHandler(IrcMessageEvent event, Cmd handler) {
    EngineResponse response = new EngineResponse(event);
    InternalRequest internalRequest;
    internalRequest = context.getBean(InternalRequest.class);
    try {
      internalRequest.init(event);
      if (!event.isPrivate()) {
        internalRequest.getUserChannel().setLastCommand(event.getMessage());
        internalRequest.getUserChannel().setLastCommandTime(new Date());
        internalRequest.saveUserChannel();
      }
      handler.handleLine(internalRequest, response);
    } catch (Exception e) {
      log.error("Command handler returned exception {}", e);
    }
  }

}
