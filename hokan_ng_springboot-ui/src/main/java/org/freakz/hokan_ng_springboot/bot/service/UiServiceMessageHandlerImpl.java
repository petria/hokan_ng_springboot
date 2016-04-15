package org.freakz.hokan_ng_springboot.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.events.ServiceRequest;
import org.freakz.hokan_ng_springboot.bot.events.ServiceResponse;
import org.freakz.hokan_ng_springboot.bot.jms.JmsEnvelope;
import org.freakz.hokan_ng_springboot.bot.jms.api.JmsServiceMessageHandler;
import org.springframework.stereotype.Service;

/**
 * Created by Petri Airio on 10.2.2015.
 * -
 */
@Service
@Slf4j
@SuppressWarnings("unchecked")
public class UiServiceMessageHandlerImpl implements JmsServiceMessageHandler {

  @Override
  public void handleJmsEnvelope(JmsEnvelope envelope) throws Exception {
    log.debug("Got message: {}", envelope);
    ServiceRequest request = envelope.getMessageIn().getServiceRequest();
    ServiceResponse response = new ServiceResponse(null);
    envelope.getMessageOut().addPayLoadObject("UI_RESPONSE", response);
  }

}
