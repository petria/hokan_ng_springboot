package org.freakz.hokan_ng_springboot.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jms.JmsMessage;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.service.ChannelService;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.service.JoinedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 *
 * Created by Petri Airio on 10.2.2015.
 */
@Controller
@Slf4j
public class IoServiceMessageHandlerImpl implements JmsServiceMessageHandler {

  @Autowired
  private ConnectionManagerService connectionManagerService;

  @Autowired
  private ChannelService channelService;

  @Autowired
  private JoinedUserService joinedUserService;

  @Override
  public JmsMessage handleJmsServiceMessage(JmsMessage jmsMessage) {
    log.debug("Handling message");
    String command = (String) jmsMessage.getPayLoadObject("COMMAND");
    JmsMessage reply = new JmsMessage();
    if (command.equals("GO_ONLINE")) {
      try {
        connectionManagerService.connect("DEVNET");
        reply.addPayLoadObject("REPLY", "Connecting to: DEVNET");
      } catch (Exception e) {
        reply.addPayLoadObject("REPLY", e.getMessage());
      }
    } else if (command.equals("TEST")) {
      List<Channel> channels = channelService.findAll();
      Channel channel = channels.get(0);
      joinedUserService.clearJoinedUsers(channel);
    }

    return reply;
  }
}
