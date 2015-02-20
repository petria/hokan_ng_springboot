package org.freakz.hokan_ng_sprintboot.jms.api;

import javax.jms.JMSException;
import javax.jms.Message;

/**
 *
 * Created by petria on 6.2.2015.
 */
public interface JmsMessageHandler {

  String getDestinationName();

  void handleJmsMessage(Message message) throws JMSException;

}
