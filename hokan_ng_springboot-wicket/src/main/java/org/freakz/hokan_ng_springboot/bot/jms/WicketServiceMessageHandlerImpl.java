package org.freakz.hokan_ng_springboot.bot.jms;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jms.api.JmsServiceMessageHandler;
import org.freakz.hokan_ng_springboot.bot.jpa.service.NetworkService;
import org.freakz.hokan_ng_springboot.bot.service.ConnectionManagerService;
import org.springframework.stereotype.Controller;

/**
 * Created by Petri Airio on 10.2.2015.
 *
 */
@Controller
@Slf4j
public class WicketServiceMessageHandlerImpl implements JmsServiceMessageHandler {

  //  @Resource
  private ConnectionManagerService connectionManagerService;

  //  @Resource
  private NetworkService networkService;

  @Override
  public void handleJmsEnvelope(JmsEnvelope envelope) throws Exception {
    envelope.getMessageOut().addPayLoadObject("REPLY", "Wicket reply!");
  }

}
