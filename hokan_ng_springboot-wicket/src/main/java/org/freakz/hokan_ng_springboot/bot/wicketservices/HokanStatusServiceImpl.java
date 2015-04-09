package org.freakz.hokan_ng_springboot.bot.wicketservices;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.enums.HokanModule;
import org.freakz.hokan_ng_springboot.bot.jms.JmsMessage;
import org.freakz.hokan_ng_springboot.bot.jms.PingResponse;
import org.freakz.hokan_ng_springboot.bot.jms.api.JmsSender;
import org.freakz.hokan_ng_springboot.bot.model.HokanStatusModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Petri Airio on 9.4.2015.
 *
 */
@Service
@Slf4j
public class HokanStatusServiceImpl implements HokanStatusService {

  @Autowired
  private JmsSender jmsSender;

  private Map<HokanModule, HokanStatusModel> statusModelMap = new HashMap<>();

  public HokanStatusServiceImpl() {
    for (HokanModule module : HokanModule.values()) {
      statusModelMap.put(module, new HokanStatusModel("<unknown>"));
    }
  }

  @Override
  public HokanStatusModel getHokanStatus(HokanModule module) {
    return statusModelMap.get(module);
  }

  @Scheduled(fixedDelay = 3000)
  private void updateStatuses() {
    for (HokanModule module : HokanModule.values()) {
      ObjectMessage objectMessage = jmsSender.sendAndGetReply(module.getQueueName(), "COMMAND", "PING");
      if (objectMessage == null) {
        statusModelMap.put(module, new HokanStatusModel("<OffLine>"));
        continue;
      }
      try {
        JmsMessage jmsMessage = (JmsMessage) objectMessage.getObject();
        PingResponse pingResponse = (PingResponse) jmsMessage.getPayLoadObject("PING_RESPONSE");
        HokanStatusModel status = new HokanStatusModel("<OnLine>");
        status.setPingResponse(pingResponse);
        statusModelMap.put(module, status);
      } catch (JMSException e) {
        log.error("jms", e);
      }
    }
  }

}
