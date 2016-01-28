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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
    String url = lunchPlaceRequest.getUrl();
    Document doc;
    try {
      Authenticator.setDefault(new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication("foo", "bar".toCharArray());
        }
      });
      doc = Jsoup.connect(url).timeout(0).userAgent(StaticStrings.HTTP_USER_AGENT).get();
    } catch (IOException e) {
      log.error("Could not fetch lunch from {}", url, e);

      return;
    }
    Elements elements = doc.getElementsByClass("entry-content");
    Elements h3 = elements.select("h3");
    for (Element element : h3) {
      String text = element.text();
      log.debug("{}", text);
      boolean gotAll = false;
      Element test = element.nextElementSibling();
      while (!gotAll) {
        // TODO better method to avoid looping forever
        String food = test.text();
        if (food.matches("Maanantai.*|Tiistai.*|Keskiviikko.*|Torstai.*|Perjantai.*")) {
          break;
        } else {
          log.debug(" ->{}", food);
        }
        test = test.nextElementSibling();
      }
      int bar = 1;
    }
    int foo = 0;

  }


}
