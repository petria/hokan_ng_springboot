package org.freakz.hokan_ng_springboot.bot.service.lunch.requesthandlers;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.enums.LunchPlace;
import org.freakz.hokan_ng_springboot.bot.models.LunchData;
import org.freakz.hokan_ng_springboot.bot.service.annotation.LunchPlaceHandler;
import org.freakz.hokan_ng_springboot.bot.service.lunch.LunchRequestHandler;
import org.freakz.hokan_ng_springboot.bot.util.StaticStrings;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by Petri Airio on 22.1.2016.
 * -
 */
@Component
@Slf4j
public class JyskaLunchPlaceHandler implements LunchRequestHandler {


  @Override
  @LunchPlaceHandler(LunchPlace = LunchPlace.LOUNAS_INFO_JYSKÄ)
  public void handleLunchPlace(LunchPlace lunchPlaceRequest, LunchData response, DateTime day) {
    response.setLunchPlace(lunchPlaceRequest);
    DateTimeFormatter fmt = DateTimeFormat.forPattern("DD.MM.yyyy");
    String url = String.format("%s&dmy=%s", lunchPlaceRequest.getUrl(), fmt.print(day));
    Document doc;
    try {
      doc = Jsoup.connect(url).userAgent(StaticStrings.HTTP_USER_AGENT).get();
    } catch (IOException e) {
      log.error("Could not fetch lunch from {}", url);
      return;
    }
//    response.setLunchMenu("Torstaisin kerneheittoa!");
    int foo = 0;
  }

}
