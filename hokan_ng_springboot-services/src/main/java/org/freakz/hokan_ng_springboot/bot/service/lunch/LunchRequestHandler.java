package org.freakz.hokan_ng_springboot.bot.service.lunch;

import org.freakz.hokan_ng_springboot.bot.enums.LunchPlace;
import org.freakz.hokan_ng_springboot.bot.models.LunchData;

/**
 * Created by Petri Airio on 22.1.2016.
 * -
 */
public interface LunchRequestHandler {

  void handleLunchPlace(LunchPlace lunchPlaceRequest, LunchData response);

}
