package org.freakz.hokan_ng_springboot.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.cmdpool.CommandPool;
import org.freakz.hokan_ng_springboot.bot.cmdpool.CommandRunnable;
import org.freakz.hokan_ng_springboot.bot.enums.HokanModule;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.jms.JmsMessage;
import org.freakz.hokan_ng_springboot.bot.jms.PingResponse;
import org.freakz.hokan_ng_springboot.bot.jms.api.JmsSender;
import org.freakz.hokan_ng_springboot.bot.models.HokanStatusModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Petri Airio on 9.4.2015.
 *
 */
@Service
@Scope("singleton")
@Slf4j
public class HokanStatusServiceImpl implements HokanStatusService, CommandRunnable {

  @Autowired
  private CommandPool commandPool;

  @Autowired
  private JmsSender jmsSender;

  @Autowired
  private HokanModuleService hokanModuleService;

  @Autowired
  private UptimeService uptimeService;

  private Map<HokanModule, HokanStatusModel> statusModelMap = new HashMap<>();
  private boolean activated = true;
  private boolean doRun;
  private HokanModule thisModule;
  @PostConstruct
  public void start() {
    for (HokanModule module : HokanModule.values()) {
      statusModelMap.put(module, new HokanStatusModel("<unknown>"));
    }
    doRun = true;
    thisModule = hokanModuleService.getHokanModule();
    commandPool.startRunnable(this, "<system>");
  }

  @Override
  public HokanStatusModel getHokanStatus(HokanModule module) {
    return statusModelMap.get(module);
  }

  @Override
  public void setActivated(boolean activated) {
    this.activated = activated;
  }


  private void updateStatuses() {
//    log.debug("thisModule: {}", thisModule);
    if (activated) {
      for (HokanModule module : HokanModule.values()) {
        if (module == HokanModule.HokanWicket) {
          continue;
        }
        if (module == thisModule) {
          HokanStatusModel status = new HokanStatusModel("<online>");
          PingResponse pingResponse = new PingResponse();
          pingResponse.setUptime(uptimeService.getUptime());
          status.setPingResponse(pingResponse);
          statusModelMap.put(module, status);
          continue;
        }
        ObjectMessage objectMessage = jmsSender.sendAndGetReply(module.getQueueName(), "COMMAND", "PING", false);
        if (objectMessage == null) {
          statusModelMap.put(module, new HokanStatusModel("<offline>"));
          continue;
        }
        try {
          JmsMessage jmsMessage = (JmsMessage) objectMessage.getObject();
          PingResponse pingResponse = (PingResponse) jmsMessage.getPayLoadObject("PING_RESPONSE");
          HokanStatusModel status = new HokanStatusModel("<online>");
          status.setPingResponse(pingResponse);
          statusModelMap.put(module, status);
        } catch (JMSException e) {
          log.error("jms", e);
        }
      }
    }
  }

  @Override
  public void handleRun(long myPid, Object args) throws HokanException {
    while (doRun) {
      updateStatuses();
      try {
        Thread.sleep(1000 * 3);
      } catch (InterruptedException e) {
        // ignore
      }

    }
  }
}
