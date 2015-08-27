package org.freakz.hokan_ng_springboot.bot.jms;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.enums.HokanModule;
import org.freakz.hokan_ng_springboot.bot.events.IrcMessageEvent;
import org.freakz.hokan_ng_springboot.bot.events.ServiceRequest;
import org.freakz.hokan_ng_springboot.bot.events.ServiceRequestType;
import org.freakz.hokan_ng_springboot.bot.jms.api.JmsSender;
import org.freakz.hokan_ng_springboot.bot.util.CommandArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Petri Airio on 9.4.2015.
 *
 */
@Service
@Slf4j
public class CommunicatorImpl implements EngineCommunicator, ServiceCommunicator {

  @Autowired
  private JmsSender jmsSender;

  @Override
  public String sendToEngine(IrcMessageEvent event) {
    try {
      jmsSender.send(HokanModule.HokanEngine.getQueueName(), "EVENT", event, false);
    } catch (Exception e) {
      log.error("error", e);
    }
    return "Sent!";
  }

  @Override
  public void sendServiceRequest(IrcMessageEvent ircEvent, ServiceRequestType requestType) {
    ServiceRequest request = new ServiceRequest(requestType, ircEvent, new CommandArgs(ircEvent.getMessage()), null);
    try {
      jmsSender.send(HokanModule.HokanServices.getQueueName(), "SERVICE_REQUEST", request, false);
    } catch (Exception e) {
      log.error("error", e);
    }
  }

}
