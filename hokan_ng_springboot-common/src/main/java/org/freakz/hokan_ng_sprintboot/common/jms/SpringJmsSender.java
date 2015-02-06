package org.freakz.hokan_ng_sprintboot.common.jms;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_sprintboot.common.jms.api.JmsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

/**
 *
 * Created by petria on 6.2.2015.
 */
@Component
@Slf4j
public class SpringJmsSender implements JmsSender {

  @Autowired
  private JmsTemplate jmsTemplate;


  public ObjectMessage sendAndGetReply(String destination, String key, String msg) {
    log.debug("{}: {} -> {}", destination, key, msg);
    Message reply = this.jmsTemplate.sendAndReceive(destination, new MessageCreator() {
          @Override
          public Message createMessage(Session session) throws JMSException {
            ObjectMessage objectMessage = session.createObjectMessage();
            JmsMessage jmsMessage = new JmsMessage();
            jmsMessage.addPayLoadObject(key, msg);
            objectMessage.setObject(jmsMessage);
            return objectMessage;
          }
        }
    );
    return (ObjectMessage) reply;
  }

  public void send(String destination, String key, String msg) {
    log.debug("{}: {} -> {}", destination, key, msg);
    this.jmsTemplate.send(destination, new MessageCreator() {
          @Override
          public Message createMessage(Session session) throws JMSException {
            ObjectMessage objectMessage = session.createObjectMessage();
            JmsMessage jmsMessage = new JmsMessage();
            jmsMessage.addPayLoadObject(key, msg);
            objectMessage.setObject(jmsMessage);
            return objectMessage;
          }
        }
    );
  }

  public void send(Destination destination, String key, String msg) {
    log.debug("{}: {} -> {}", destination, key, msg);
    this.jmsTemplate.send(destination, new MessageCreator() {
      @Override
      public Message createMessage(Session session) throws JMSException {
        ObjectMessage objectMessage = session.createObjectMessage();
        JmsMessage jmsMessage = new JmsMessage();
        jmsMessage.addPayLoadObject(key, msg);
        objectMessage.setObject(jmsMessage);
        return objectMessage;
      }
    });
  }

}
