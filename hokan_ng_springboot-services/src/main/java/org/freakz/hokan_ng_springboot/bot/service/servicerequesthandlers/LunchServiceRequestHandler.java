package org.freakz.hokan_ng_springboot.bot.service.servicerequesthandlers;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.enums.LunchPlace;
import org.freakz.hokan_ng_springboot.bot.events.ServiceRequest;
import org.freakz.hokan_ng_springboot.bot.events.ServiceRequestType;
import org.freakz.hokan_ng_springboot.bot.events.ServiceResponse;
import org.freakz.hokan_ng_springboot.bot.service.annotation.ServiceMessageHandler;
import org.freakz.hokan_ng_springboot.bot.service.lunch.LunchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Petri Airio on 28.1.2016.
 * -
 */
@Component
@Slf4j
public class LunchServiceRequestHandler {

  @Autowired
  private LunchService lunchService;

  @ServiceMessageHandler(ServiceRequestType = ServiceRequestType.LUNCH_PLACES_REQUEST)
  public void handleLunchPlacesServiceRequest(ServiceRequest request, ServiceResponse response) {
    log.debug("Handling: {}", request);
    List<LunchPlace> placeList = lunchService.getLunchPlaces();
    response.setResponseData(request.getType().getResponseDataKey(), placeList);
  }

}
