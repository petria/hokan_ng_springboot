package org.freakz.hokan_ng_springboot.bot.wicketservices;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.enums.HokanModule;
import org.freakz.hokan_ng_springboot.bot.jms.JmsMessage;
import org.freakz.hokan_ng_springboot.bot.jms.PingResponse;
import org.freakz.hokan_ng_springboot.bot.jms.api.JmsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

/**
 * Created by Petri Airio on 9.4.2015.
 *
 */
@Service
@Slf4j
public class HokanStatusServiceImpl implements HokanStatusService {

  @Autowired
  private JmsSender jmsSender;

  private String engineStatus = "<unknown>";
  private String ioStatus = "<unknown>";
  private String servicesStatus = "<unknown>";

  public HokanStatusServiceImpl() {
  }

  @Override
  public String getHokanStatus(HokanModule module) {
    switch (module) {
      case HokanEngine:
        return engineStatus;
      case HokanIo:
        return ioStatus;
      case HokanServices:
        return servicesStatus;
    }
    return "<unknown module>";
  }

  @Scheduled(fixedDelay = 5000)
  private void updateStatuses() {
    ObjectMessage objectMessage = jmsSender.sendAndGetReply("HokanNGIoQueue", "COMMAND", "PING");
    if (objectMessage == null) {
      ioStatus = "<offline>";
      return;
    }
    try {
      JmsMessage jmsMessage = (JmsMessage) objectMessage.getObject();
      if (jmsMessage != null) {
        PingResponse pingResponse = (PingResponse) jmsMessage.getPayLoadObject("PING_RESPONSE");
        if (pingResponse != null) {
          ioStatus = pingResponse.getReply();
        } else {
          ioStatus = "<error>";
        }
      }
    } catch (JMSException e) {
      log.error("jms", e);
    }

  }

}
