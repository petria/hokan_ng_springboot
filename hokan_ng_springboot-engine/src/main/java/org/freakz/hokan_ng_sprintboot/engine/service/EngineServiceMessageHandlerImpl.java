package org.freakz.hokan_ng_sprintboot.engine.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jms.JmsMessage;
import org.freakz.hokan_ng_springboot.bot.service.JmsServiceMessageHandler;
import org.springframework.stereotype.Component;

/**
 *
 * Created by petria on 10.2.2015.
 */
@Component

@Slf4j
public class EngineServiceMessageHandlerImpl implements JmsServiceMessageHandler {

  @Override
  public JmsMessage handleJmsServiceMessage(JmsMessage jmsMessage) {
    log.debug("Handling message");
    JmsMessage reply = new JmsMessage();
    reply.addPayLoadObject("REPLY", "reply");
    return reply;
  }
}
