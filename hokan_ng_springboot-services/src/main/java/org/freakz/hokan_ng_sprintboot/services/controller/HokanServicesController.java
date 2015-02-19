package org.freakz.hokan_ng_sprintboot.services.controller;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_sprintboot.common.jms.JmsMessage;
import org.freakz.hokan_ng_sprintboot.common.jms.api.JmsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

/**
 * Created by petria on 29.1.2015.
 * g
 */
@Controller
@Slf4j
public class HokanServicesController {

  @Autowired
  private JmsSender jmsSender;

  @RequestMapping("/test")
  @ResponseBody
  public String goOnline() throws JMSException {
    log.info("Sending Sync");
    ObjectMessage reply = jmsSender.sendAndGetReply("HokanNGIoQueue", "COMMAND", "GO_ONLINE");
    if (reply != null) {
      JmsMessage jmsMessage = (JmsMessage) reply.getObject();
      return "reply: " + jmsMessage.getPayLoadObject("REPLY");
    }
    return "No reply!";
  }

  @RequestMapping("/test2")
  @ResponseBody
  public String testASync() throws JMSException {
    log.info("Sending ASync");
    jmsSender.send("HokanNGIoQueue", "TEXT", "fdfdfdffffd");
    return "message sent!";
  }

  @RequestMapping("/test3")
  @ResponseBody
  public String testASync2() throws JMSException {
    log.info("Sending ASync");
    jmsSender.send("HokanNGEngineQueue", "TEXT", "To engine!");
    return "message sent!";
  }

}
