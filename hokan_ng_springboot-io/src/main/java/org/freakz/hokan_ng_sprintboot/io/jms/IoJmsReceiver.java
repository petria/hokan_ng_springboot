package org.freakz.hokan_ng_sprintboot.io.jms;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_sprintboot.jms.JmsMessage;
import org.freakz.hokan_ng_sprintboot.jms.SpringJmsReceiver;
import org.freakz.hokan_ng_sprintboot.jms.api.JmsSender;
import org.freakz.hokan_ng_sprintboot.service.JmsServiceMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

/**
 *
 * Created by petria on 5.2.2015.
 */
@RestController
@Slf4j
public class IoJmsReceiver extends SpringJmsReceiver {

  @Autowired
  private JmsSender jmsSender;

  @Autowired
  private JmsServiceMessageHandler jmsServiceMessageHandler;


  @Override
  public String getDestinationName() {
    return "HokanNGIoQueue";
  }

  @Override
  public void handleJmsMessage(Message message) throws JMSException {
    ObjectMessage objectMessage = (ObjectMessage) message;
    JmsMessage jmsMessage = (JmsMessage) objectMessage.getObject();
    JmsMessage jmsReplyMessage = null;
    try {
      jmsReplyMessage = jmsServiceMessageHandler.handleJmsServiceMessage(jmsMessage);
    } catch (Exception e) {
      log.error("Something went wrong!");
    }
    Destination replyTo = message.getJMSReplyTo();
    log.debug("got message: {}, replyTo: {}", jmsMessage, replyTo);
    if (replyTo != null) {
      if (jmsReplyMessage != null) {
        jmsSender.sendJmsMessage(replyTo, jmsReplyMessage);
      }
    }

  }

  @RequestMapping("/iotest")
  @ResponseBody
  public String testASync() throws JMSException {
    jmsSender.send("HokanNGServicesQueue", "MESSGE", "FUFUFUFUF");
    log.info("ufufufufufufuf");
    return "gfoo";
  }


}
