package org.freakz.hokan_ng_springboot.bot.service;

import org.freakz.hokan_ng_springboot.bot.service.nimipaiva.NimipaivaService;
import org.freakz.hokan_ng_springboot.bot.service.nimipaiva.NimipaivaServiceImpl;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by Petri Airio on 5.10.2015.
 *
 */
public class NimipaivaServiceTest {


  private NimipaivaService nimipaivaService;

  @Before
  public void setup() {
    this.nimipaivaService = new NimipaivaServiceImpl();
  }


  @Test
  public void testGetNamesForDay() {
    DateTime now = DateTime.now();
    List<String> names = nimipaivaService.getNamesForDay(now);
    org.junit.Assert.assertNotNull("Must have list of names", names);;

//    Assert.notEmpty("must have names list", names);
  }


}
