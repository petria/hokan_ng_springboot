package org.freakz.hokan_ng_springboot.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.command.CommandHandlerService;
import org.freakz.hokan_ng_springboot.bot.command.handlers.Cmd;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.events.IrcMessageEvent;
import org.freakz.hokan_ng_springboot.bot.jms.JmsEnvelope;
import org.freakz.hokan_ng_springboot.bot.jms.api.JmsSender;
import org.freakz.hokan_ng_springboot.bot.jms.api.JmsServiceMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * Created by Petri Airio on 10.2.2015.
 *
 */
@Controller
@Slf4j
public class EngineServiceMessageHandlerImpl implements JmsServiceMessageHandler {

  @Autowired
  private JmsSender jmsSender;

  @Autowired
  private ApplicationContext context;

  @Autowired
  private CommandHandlerService commandHandlerService;


  @PostConstruct
  public void postConstruct() {
//    hokanModuleService.setHokanModule(HokanModule.HokanEngine);
  }

  @Override
  public void handleJmsEnvelope(JmsEnvelope envelope) throws Exception {
    IrcMessageEvent event = (IrcMessageEvent) envelope.getMessageIn().getPayLoadObject("EVENT");
//    log.debug("Handling event: {}", event);
    if (event == null) {
      log.debug("Nothing to do!");
    }
    Cmd handler = commandHandlerService.getCommandHandler(event.getMessage());

    if (handler != null) {
      EngineResponse response = new EngineResponse(event);
      InternalRequest internalRequest;
      internalRequest = context.getBean(InternalRequest.class);
      try {
        internalRequest.init(event);
        if (!event.isPrivate()) {
          internalRequest.getUserChannel().setLastCommand(handler.getName());
          internalRequest.getUserChannel().setLastCommandTime(new Date());
          internalRequest.updateUserChannel();
        }
        handler.handleLine(internalRequest, response);
      } catch (Exception e) {
        log.error("Command handler returned exception {}", e);
      }
    }
  }

}
