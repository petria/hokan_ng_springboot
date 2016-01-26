package org.freakz.hokan_ng_springboot.bot.service.lunch.requesthandlers;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.enums.LunchPlace;
import org.freakz.hokan_ng_springboot.bot.models.LunchData;
import org.freakz.hokan_ng_springboot.bot.service.annotation.LunchPlaceHandler;
import org.freakz.hokan_ng_springboot.bot.service.lunch.LunchRequestHandler;
import org.freakz.hokan_ng_springboot.bot.util.StaticStrings;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * Created by Petri Airio on 26.1.2016.
 * -
 */
@Component
@Slf4j
public class HarmooniLunchPlaceHandler implements LunchRequestHandler {

  @Override
  @LunchPlaceHandler(LunchPlace = LunchPlace.LOUNAS_INFO_HARMOONI)
  public void handleLunchPlace(LunchPlace lunchPlaceRequest, LunchData response, DateTime day) {
    response.setLunchPlace(lunchPlaceRequest);
    response.setDay(day);
    String url = lunchPlaceRequest.getUrl();
    Document doc;
    try {
      Authenticator.setDefault(new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication("foo", "bar".toCharArray());
        }
      });
      doc = Jsoup.connect(url).userAgent(StaticStrings.HTTP_USER_AGENT).get();
    } catch (IOException e) {
      log.error("Could not fetch lunch from {}", url, e);

      return;
    }
    int foo = 0;

  }


}
