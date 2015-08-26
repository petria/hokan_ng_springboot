package org.freakz.hokan_ng_springboot.bot.jms;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.TvNotifyRequest;
import org.freakz.hokan_ng_springboot.bot.service.ConnectionManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by petria on 5.2.2015.
 *
 */
@Component
@Slf4j
public class IoJmsReceiver extends SpringJmsReceiver {

  @Autowired
  private ConnectionManagerService connectionManagerService;

  @Override
  public String getDestinationName() {
    return "HokanNGIoQueue";
  }

  @Override
  public void handleJmsEnvelope(JmsEnvelope envelope) throws Exception {
    if (envelope.getMessageIn().getPayLoadObject("ENGINE_RESPONSE") != null) {
      handleEngineReply(envelope);
    } else if (envelope.getMessageIn().getPayLoadObject("TV_NOTIFY_REQUEST") != null) {
      handleTvNotify(envelope);
    }

  }

  private void handleTvNotify(JmsEnvelope envelope) {
    TvNotifyRequest tvNotifyRequest = (TvNotifyRequest) envelope.getMessageIn().getPayLoadObject("TV_NOTIFY_REQUEST");
    log.debug("handling TvNotifyRequest: {}", tvNotifyRequest);
    connectionManagerService.handleTvNotifyRequest(tvNotifyRequest);
  }

  private void handleEngineReply(JmsEnvelope envelope) {
    EngineResponse response = (EngineResponse) envelope.getMessageIn().getPayLoadObject("ENGINE_RESPONSE");
//    log.debug("handling engine response: {}", response);
    connectionManagerService.handleEngineResponse(response);
  }

}
