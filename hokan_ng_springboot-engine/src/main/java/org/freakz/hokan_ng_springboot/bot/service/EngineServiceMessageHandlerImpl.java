package org.freakz.hokan_ng_springboot.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.command.CommandHandlerService;
import org.freakz.hokan_ng_springboot.bot.command.handlers.Cmd;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.events.IrcMessageEvent;
import org.freakz.hokan_ng_springboot.bot.exception.HokanEngineException;
import org.freakz.hokan_ng_springboot.bot.jms.JmsMessage;
import org.freakz.hokan_ng_springboot.bot.jms.api.JmsSender;
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
  private JmsSender jmsSender;

  @Autowired
  private ApplicationContext context;

  @Autowired
  private CommandHandlerService commandHandlerService;

  @Override
  public JmsMessage handleJmsServiceMessage(JmsMessage jmsMessage) {
    IrcMessageEvent event = (IrcMessageEvent) jmsMessage.getPayLoadObject("EVENT");
    log.debug("Handling event: {}", event);

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
        HokanEngineException engineException = new HokanEngineException(e, handler.getClass().getName());
        response.setException(engineException.toString());
        log.error("Command handler returned exception {}", e);
      }
      sendReply(response);
    }
    return null;
  }

  private void sendReply(EngineResponse response) {
    log.debug("Sending response: {}", response);
    jmsSender.send("HokanNGIoQueue", "ENGINE_RESPONSE", response);
  }

}