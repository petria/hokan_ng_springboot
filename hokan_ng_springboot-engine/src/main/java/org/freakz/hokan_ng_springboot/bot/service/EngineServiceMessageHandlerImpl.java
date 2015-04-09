package org.freakz.hokan_ng_springboot.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.events.IrcMessageEvent;
import org.freakz.hokan_ng_springboot.bot.jms.JmsMessage;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.service.ChannelService;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.service.JoinedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by Petri Airio on 10.2.2015.
 */
@Controller
@Slf4j
public class EngineServiceMessageHandlerImpl implements JmsServiceMessageHandler {

  @Autowired
  private ChannelService channelService;

  @Autowired
  private JoinedUserService joinedUserService;

  @Override
  public JmsMessage handleJmsServiceMessage(JmsMessage jmsMessage) {
    IrcMessageEvent event = (IrcMessageEvent) jmsMessage.getPayLoadObject("EVENT");
    log.debug("Handling event, command: {}", event);
    JmsMessage reply = new JmsMessage();
    reply.addPayLoadObject("REPLY", "Engine reply!!");
    return reply;
  }
}
