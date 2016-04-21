package org.freakz.hokan_ng_springboot.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jms.JmsEnvelope;
import org.freakz.hokan_ng_springboot.bot.jms.api.JmsServiceMessageHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Petri Airio on 10.2.2015.
 * -
 */
@Service
//@Scope("singleton")
@Slf4j
@SuppressWarnings("unchecked")
public class UiServiceMessageHandlerImpl implements JmsServiceMessageHandler {

  private final List<BroadcastListener> listeners = new CopyOnWriteArrayList<>();

  public void register(BroadcastListener listener) {
    log.debug("register: {}", listener);
    listeners.add(listener);
  }

  public void unregister(BroadcastListener listener) {
    log.debug("unregister: {}", listener);
    listeners.remove(listener);
  }

  public void broadcast(final String message) {
    for (BroadcastListener listener : listeners) {
      log.debug("sending msg to: {}", listener);
      listener.receiveBroadcast(message);
    }
  }

  public interface BroadcastListener {
    void receiveBroadcast(String message);
  }


  @Override
  public void handleJmsEnvelope(JmsEnvelope envelope) throws Exception {
    log.debug("Got message: {}", envelope);
    String msg = (String) envelope.getMessageIn().getPayload().get("TEXT");
      if (msg != null) {
        broadcast(msg);
      }
    envelope.getMessageOut().addPayLoadObject("UI_RESPONSE", "Message sent!");
  }

}
