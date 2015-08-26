package org.freakz.hokan_ng_springboot.bot.jms;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.NotifyRequest;
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
    } else if (envelope.getMessageIn().getPayLoadObject("STATS_NOTIFY_REQUEST") != null) {
      handleStatsNotify(envelope);
    }

  }

  private void handleStatsNotify(JmsEnvelope envelope) {
    NotifyRequest notifyRequest = (NotifyRequest) envelope.getMessageIn().getPayLoadObject("STATS_NOTIFY_REQUEST");
    log.debug("handling NotifyRequest: {}", notifyRequest);
    connectionManagerService.handleStatsNotifyRequest(notifyRequest);

  }

  private void handleTvNotify(JmsEnvelope envelope) {
    NotifyRequest notifyRequest = (NotifyRequest) envelope.getMessageIn().getPayLoadObject("TV_NOTIFY_REQUEST");
    log.debug("handling NotifyRequest: {}", notifyRequest);
    connectionManagerService.handleTvNotifyRequest(notifyRequest);
  }

  private void handleEngineReply(JmsEnvelope envelope) {
    EngineResponse response = (EngineResponse) envelope.getMessageIn().getPayLoadObject("ENGINE_RESPONSE");
    connectionManagerService.handleEngineResponse(response);
  }

}
