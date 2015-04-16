package org.freakz.hokan_ng_springboot.bot.jms.api;

import org.freakz.hokan_ng_springboot.bot.jms.JmsMessage;

import javax.jms.Destination;
import javax.jms.ObjectMessage;

/**
 *
 * Created by petria on 5.2.2015.
 */
public interface JmsSender {

  ObjectMessage sendAndGetReply(String destination, String key, Object object, boolean deliveryPersistent);

  void send(String destination, String key, Object object, boolean deliveryPersistent);

  void send(Destination destination, String key, Object object);

  void sendJmsMessage(Destination destination, JmsMessage jmsMessage);

}
