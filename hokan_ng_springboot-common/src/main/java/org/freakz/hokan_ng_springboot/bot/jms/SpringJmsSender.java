package org.freakz.hokan_ng_springboot.bot.jms;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jms.api.JmsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.ObjectMessage;

/**
 *
 * Created by petria on 6.2.2015.
 */
@Component
@Slf4j
public class SpringJmsSender implements JmsSender {

  @Autowired
  private JmsTemplate jmsTemplate;


  public ObjectMessage sendAndGetReply(String destination, String key, Object object, boolean deliveryPersistent) {
//    log.debug("{}: {} -> {}", destination, key, object);
    this.jmsTemplate.setReceiveTimeout(1 * 1000);
    this.jmsTemplate.setDeliveryPersistent(deliveryPersistent);
    Message reply = this.jmsTemplate.sendAndReceive(destination, session -> {
          ObjectMessage objectMessage = session.createObjectMessage();
          JmsMessage jmsMessage = new JmsMessage();
          jmsMessage.addPayLoadObject(key, object);
          objectMessage.setObject(jmsMessage);
          return objectMessage;
        }
    );
    return (ObjectMessage) reply;
  }

  public void send(String destination, String key, Object object, boolean deliveryPersistent) {
//    log.debug("{}: {} -> {}", destination, key, object);
    this.jmsTemplate.setDeliveryPersistent(deliveryPersistent);
    this.jmsTemplate.send(destination, session -> {
          ObjectMessage objectMessage = session.createObjectMessage();
          JmsMessage jmsMessage = new JmsMessage();
          jmsMessage.addPayLoadObject(key, object);
          objectMessage.setObject(jmsMessage);
          return objectMessage;
        }
    );
  }

  public void send(Destination destination, String key, Object object) {
//    log.debug("{}: {} -> {}", destination, key, object);
    this.jmsTemplate.send(destination, session -> {
      ObjectMessage objectMessage = session.createObjectMessage();
      JmsMessage jmsMessage = new JmsMessage();
      jmsMessage.addPayLoadObject(key, object);
      objectMessage.setObject(jmsMessage);
      return objectMessage;
    });
  }

  public void sendJmsMessage(Destination destination, JmsMessage jmsMessage) {
//    log.debug("{}: ", destination);
    this.jmsTemplate.send(destination, session -> {
      ObjectMessage objectMessage = session.createObjectMessage();
      objectMessage.setObject(jmsMessage);
      return objectMessage;
    });
  }

}
