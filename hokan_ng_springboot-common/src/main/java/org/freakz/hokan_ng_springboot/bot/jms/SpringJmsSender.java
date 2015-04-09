package org.freakz.hokan_ng_springboot.bot.jms;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jms.api.JmsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 *
 * Created by petria on 6.2.2015.
 */
@Component
@Slf4j
public class SpringJmsSender implements JmsSender {

  @Autowired
  private JmsTemplate jmsTemplate;


  public ObjectMessage sendAndGetReply(String destination, String key, Object object) {
    log.debug("{}: {} -> {}", destination, key, object);
    this.jmsTemplate.setReceiveTimeout(30 * 1000);
    Message reply = this.jmsTemplate.sendAndReceive(destination, new MessageCreator() {
          @Override
          public Message createMessage(Session session) throws JMSException {
            ObjectMessage objectMessage = session.createObjectMessage();
            JmsMessage jmsMessage = new JmsMessage();
            jmsMessage.addPayLoadObject(key, object);
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

  public void sendJmsMessage(Destination destination, JmsMessage jmsMessage) {
    log.debug("{}: ", destination);
    this.jmsTemplate.send(destination, new MessageCreator() {
      @Override
      public Message createMessage(Session session) throws JMSException {
        ObjectMessage objectMessage = session.createObjectMessage();
        objectMessage.setObject(jmsMessage);
        return objectMessage;
      }
    });
  }

}
