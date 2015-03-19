package org.freakz.hokan_ng_springboot.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jms.JmsMessage;
import org.springframework.stereotype.Controller;

/**
 * Created by Petri Airio on 10.2.2015.
 *
 */
@Controller
@Slf4j
public class ServicesServiceMessageHandlerImpl implements JmsServiceMessageHandler {

  @Override
  public JmsMessage handleJmsServiceMessage(JmsMessage jmsMessage) {
    log.debug("Handling message");
    JmsMessage reply = new JmsMessage();
    return reply;
  }
}
