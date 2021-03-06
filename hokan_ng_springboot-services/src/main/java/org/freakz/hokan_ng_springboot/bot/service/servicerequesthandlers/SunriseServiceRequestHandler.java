package org.freakz.hokan_ng_springboot.bot.service.servicerequesthandlers;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.events.ServiceRequest;
import org.freakz.hokan_ng_springboot.bot.events.ServiceRequestType;
import org.freakz.hokan_ng_springboot.bot.events.ServiceResponse;
import org.freakz.hokan_ng_springboot.bot.service.DayChangedService;
import org.freakz.hokan_ng_springboot.bot.service.annotation.ServiceMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petri Airio on 11.5.2016.
 * -
 */
@Component
@Slf4j
public class SunriseServiceRequestHandler {

  @Autowired
  private DayChangedService dayChangedService;

  @ServiceMessageHandler(ServiceRequestType = ServiceRequestType.SUNRISE_SERVICE_REQUEST)
  public void handleLunchPlacesServiceRequest(ServiceRequest request, ServiceResponse response) {
    String city = (String) request.getParameters()[0];
    List<String> cityList = new ArrayList<>();
    cityList.add(city);
    String sunrise = dayChangedService.getSunriseTexts(cityList);
    response.setResponseData(request.getType().getResponseDataKey(), sunrise);
  }

}
