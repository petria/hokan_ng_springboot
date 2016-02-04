package org.freakz.hokan_ng_springboot.bot.service;

import org.freakz.hokan_ng_springboot.bot.enums.LunchPlace;
import org.freakz.hokan_ng_springboot.bot.models.LunchData;
import org.freakz.hokan_ng_springboot.bot.service.lunch.requesthandlers.HarmooniLunchPlaceHandler;
import org.freakz.hokan_ng_springboot.bot.service.lunch.requesthandlers.HelsinkiTerminaali2RequestHandler;
import org.freakz.hokan_ng_springboot.bot.service.lunch.requesthandlers.JyskaLunchPlaceHandler;
import org.freakz.hokan_ng_springboot.bot.service.lunch.LunchRequestHandler;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Petri Airio on 25.1.2016.
 * -
 */
public class LunchServiceTest {



  @Test
  public void testJyskäFetch() {
    LunchRequestHandler lunchRequestHandler  = new JyskaLunchPlaceHandler();
    LunchData response = new LunchData();
    lunchRequestHandler.handleLunchPlace(LunchPlace.LOUNAS_INFO_JYSKÄ, response, DateTime.now().minusDays(1));
    assertEquals(LunchPlace.LOUNAS_INFO_JYSKÄ, response.getLunchPlace());
  }

  @Test
  public void testHarmooniFetch() {
    LunchRequestHandler lunchRequestHandler  = new HarmooniLunchPlaceHandler();
    LunchData response = new LunchData();
    lunchRequestHandler.handleLunchPlace(LunchPlace.LOUNAS_INFO_HARMOONI, response, DateTime.now().minusDays(1));
    assertEquals(LunchPlace.LOUNAS_INFO_HARMOONI, response.getLunchPlace());
  }

  @Test
  public void testHelsinkiTerminaali2() {
    LunchRequestHandler lunchRequestHandler  = new HelsinkiTerminaali2RequestHandler();
    LunchData response = new LunchData();
    lunchRequestHandler.handleLunchPlace(LunchPlace.LOUNAS_INFO_HKI_TERMINAALI2, response, DateTime.now());
    assertEquals(LunchPlace.LOUNAS_INFO_HKI_TERMINAALI2, response.getLunchPlace());

  }

}
