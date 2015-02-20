package org.freakz.hokan_ng_sprintboot.io.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_sprintboot.jms.JmsMessage;
import org.freakz.hokan_ng_sprintboot.repository.NetworkService;
import org.freakz.hokan_ng_sprintboot.service.ConnectionManagerService;
import org.freakz.hokan_ng_sprintboot.service.JmsServiceMessageHandler;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 *
 * Created by Petri Airio on 10.2.2015.
 */
@Controller
@Slf4j
public class IoServiceMessageHandlerImpl implements JmsServiceMessageHandler {

  @Resource
  private ConnectionManagerService connectionManagerService;

  @Resource
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
