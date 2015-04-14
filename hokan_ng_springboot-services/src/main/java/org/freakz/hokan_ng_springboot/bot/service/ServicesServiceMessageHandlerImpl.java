package org.freakz.hokan_ng_springboot.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jms.JmsEnvelope;
import org.freakz.hokan_ng_springboot.bot.jms.api.JmsServiceMessageHandler;
import org.springframework.stereotype.Controller;

/**
 * Created by Petri Airio on 10.2.2015.
 *
 */
@Controller
@Slf4j
public class ServicesServiceMessageHandlerImpl implements JmsServiceMessageHandler {


  @Override
  public void handleJmsEnvelope(JmsEnvelope envelope) throws Exception {
    log.debug("Handling envelope");

  }

}
