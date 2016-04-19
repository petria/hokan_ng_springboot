package org.freakz.hokan_ng_springboot.bot.service;

import com.vaadin.ui.UI;
import lombok.extern.slf4j.Slf4j;
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

  private UI ui;

  public void setUI(UI ui) {
    this.ui = ui;
  }

  @Override
  public void handleJmsEnvelope(JmsEnvelope envelope) throws Exception {
    log.debug("Got message: {}", envelope);
    if (ui != null) {
      String msg = (String) envelope.getMessageIn().getPayload().get("TEXT");
      if (msg != null) {
        ui.showNotification("Msg: " + msg);
      }
    } else {
      log.warn("No UI !!");
    }
    envelope.getMessageOut().addPayLoadObject("UI_RESPONSE", "Message sent!");
  }

}
