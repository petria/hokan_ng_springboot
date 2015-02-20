package org.freakz.hokan_ng_sprintboot.jms.api;

import org.freakz.hokan_ng_sprintboot.jms.JmsMessage;

import javax.jms.Destination;
import javax.jms.ObjectMessage;

/**
 *
 * Created by petria on 5.2.2015.
 */
public interface JmsSender {

  ObjectMessage sendAndGetReply(String destination, String key, String msg);

  void send(String destination, String key, String msg);

  void send(Destination destination, String key, String msg);

  void sendJmsMessage(Destination destination, JmsMessage jmsMessage);

}
