package org.freakz.hokan_ng_sprintboot.services;

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
public class TestController {

  /*  @Autowired
    private ServicesSender servicesSender;
  */
  @Autowired
  private JmsSender jmsSender;

  @RequestMapping("/test")
  @ResponseBody
  public String testSync() throws JMSException {
    log.info("Sending Sync");
    ObjectMessage reply = jmsSender.sendAndGetReply("HokanNGIoQueue", "TEXT", "fdfdfdffffd");
    JmsMessage jmsMessage = (JmsMessage) reply.getObject();
    return "reply: " + jmsMessage.getPayLoadObject("REPLY");
  }

  @RequestMapping("/test2")
  @ResponseBody
  public String testASync() throws JMSException {
    log.info("Sending ASync");
    jmsSender.send("HokanNGIoQueue", "TEXT", "fdfdfdffffd");
    return "message sent!";
  }

}
