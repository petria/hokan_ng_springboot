package org.freakz.hokan_ng_springboot.bot.jms.api;

import org.freakz.hokan_ng_springboot.bot.jms.JmsEnvelope;

/**
 *
 * Created by petria on 6.2.2015.
 */
public interface JmsMessageHandler {

  String getDestinationName();

  void handleJmsEnvelope(JmsEnvelope envelope) throws Exception;


}
