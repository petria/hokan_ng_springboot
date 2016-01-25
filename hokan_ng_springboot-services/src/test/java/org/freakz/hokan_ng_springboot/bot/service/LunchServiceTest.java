package org.freakz.hokan_ng_springboot.bot.service;

import junit.framework.Assert;
import org.freakz.hokan_ng_springboot.bot.enums.LunchPlace;
import org.freakz.hokan_ng_springboot.bot.models.LunchData;
import org.freakz.hokan_ng_springboot.bot.service.lunch.JyskaLunchPlaceHandler;
import org.freakz.hokan_ng_springboot.bot.service.lunch.LunchRequestHandler;
import org.freakz.hokan_ng_springboot.bot.service.lunch.LunchServiceImpl;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * Created by Petri Airio on 25.1.2016.
 * -
 */
public class LunchServiceTest {

  private LunchRequestHandler lunchRequestHandler;

  @Before
  public void before() {
    this.lunchRequestHandler = new JyskaLunchPlaceHandler();
  }

  @Test
  public void testFetch() {
    LunchData response = new LunchData();
    lunchRequestHandler.handleLunchPlace(LunchPlace.LOUNAS_INFO_JYSKÄ, response);
    assertEquals(LunchPlace.LOUNAS_INFO_JYSKÄ, response.getLunchPlace());

  }

}
