package org.freakz.hokan_ng_sprintboot.engine.jms;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_sprintboot.common.jms.JmsMessage;
import org.freakz.hokan_ng_sprintboot.common.jms.SpringJmsReceiver;
import org.freakz.hokan_ng_sprintboot.common.jms.api.JmsSender;
import org.freakz.hokan_ng_sprintboot.engine.service.EngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

/**
 *
 * Created by petria on 5.2.2015.
 */
@Component
@Slf4j
public class EngineJmsReceiver extends SpringJmsReceiver {

  @Autowired
  private JmsSender jmsSender;

  @Autowired
  private EngineService engineService;

  @Override
  public String getDestinationName() {
    return "HokanNGEngineQueue";
  }

  @Override
  public void handleJmsMessage(Message message) throws JMSException {
    ObjectMessage objectMessage = (ObjectMessage) message;
    JmsMessage jmsMessage = (JmsMessage) objectMessage.getObject();
    engineService.handleJmsMessage(jmsMessage);
    Destination replyTo = message.getJMSReplyTo();
    log.debug("got message: {}, replyTo: {}", jmsMessage, replyTo);
    if (replyTo != null) {
      jmsSender.send(replyTo, "REPLY", "reply: " + jmsMessage.getPayLoadObject("TEXT"));
    }

  }

}
