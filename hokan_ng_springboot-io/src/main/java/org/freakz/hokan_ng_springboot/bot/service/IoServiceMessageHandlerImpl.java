package org.freakz.hokan_ng_springboot.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jms.JmsMessage;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.NetworkService;
import org.springframework.stereotype.Controller;

/**
 *
 * Created by Petri Airio on 10.2.2015.
 */
@Controller
@Slf4j
public class IoServiceMessageHandlerImpl implements JmsServiceMessageHandler {

//  @Resource
  private ConnectionManagerService connectionManagerService;

//  @Resource
  private NetworkService networkService;

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
    }
    return reply;
  }
}
