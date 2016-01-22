package org.freakz.hokan_ng_springboot.bot.service.lunch;

import org.freakz.hokan_ng_springboot.bot.enums.LunchPlace;
import org.freakz.hokan_ng_springboot.bot.models.LunchData;
import org.freakz.hokan_ng_springboot.bot.service.annotation.LunchPlaceHandler;
import org.springframework.stereotype.Component;

/**
 * Created by Petri Airio on 22.1.2016.
 * -
 */
@Component
public class JyskaLunchPlaceHandler implements LunchRequestHandler {


  @Override
  @LunchPlaceHandler(LunchPlace = LunchPlace.LOUNAS_INFO_JYSKÄ)
  public void handleLunchPlace(LunchPlace lunchPlaceRequest, LunchData response) {
    response.setLunchPlace(lunchPlaceRequest);
  }

}
