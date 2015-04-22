package org.freakz.hokan_ng_springboot.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jms.JmsEnvelope;
import org.freakz.hokan_ng_springboot.bot.jms.api.JmsServiceMessageHandler;
import org.freakz.hokan_ng_springboot.bot.service.metar.MetarDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by Petri Airio on 10.2.2015.
 *
 */
@Controller
@Slf4j
public class ServicesServiceMessageHandlerImpl implements JmsServiceMessageHandler {

  @Autowired
  private MetarDataService metarDataService;

  @Override
  public void handleJmsEnvelope(JmsEnvelope envelope) throws Exception {
    String command = envelope.getMessageIn().getCommand();
    if (command.equals("METAR")) {

    }
    log.debug("Handling envelope");

  }

}
