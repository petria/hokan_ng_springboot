package org.freakz.hokan_ng_springboot.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.events.ServiceRequest;
import org.freakz.hokan_ng_springboot.bot.events.ServiceResponse;
import org.freakz.hokan_ng_springboot.bot.jms.JmsEnvelope;
import org.freakz.hokan_ng_springboot.bot.jms.api.JmsServiceMessageHandler;
import org.freakz.hokan_ng_springboot.bot.models.MetarData;
import org.freakz.hokan_ng_springboot.bot.service.metar.MetarDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

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
    ServiceRequest request = envelope.getMessageIn().getServiceRequest();
    if (request.getType() == ServiceRequest.ServiceRequestType.METAR_REQUEST) {
      List<MetarData> data = metarDataService.getMetarData(request.getParameters());
      ServiceResponse response = new ServiceResponse();
      response.setResponseData("METAR_DATA", data);
      envelope.getMessageOut().addPayLoadObject("SERVICE_RESPONSE", response);
    }
    log.debug("Handling envelope");

  }

}
