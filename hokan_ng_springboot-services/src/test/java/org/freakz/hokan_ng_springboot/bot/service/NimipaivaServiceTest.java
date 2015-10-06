package org.freakz.hokan_ng_springboot.bot.service;

import org.freakz.hokan_ng_springboot.bot.service.nimipaiva.NimipaivaService;
import org.freakz.hokan_ng_springboot.bot.service.nimipaiva.NimipaivaServiceImpl;
import org.joda.time.DateTime;
import org.junit.Assert;
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
    this.nimipaivaService.loadNames();
  }


  @Test
  public void testGetNamesForDay() {
    // 3.1. Elmo, Elmeri, Elmer
    DateTime now = DateTime.now().withDayOfMonth(1).withMonthOfYear(3);
    List<String> names = nimipaivaService.getNamesForDay(now);
    Assert.assertNotNull("Must have list of names", names);;
    Assert.assertEquals("list size", 3, names.size());
    Assert.assertEquals("name 1", "Elmo", names.get(0));
    Assert.assertEquals("name 2", "Elmeri", names.get(1));
    Assert.assertEquals("name 1", "Elmer", names.get(2));
  }

  @Test
  public void testFindDayForName() {
    // 29.6. Pekka, Petri, Petra, Petteri, Pietari, Pekko
    DateTime dateTime = nimipaivaService.findDayForName("Petri");
    Assert.assertNotNull("Must have DateTime", dateTime);
    Assert.assertEquals("Month must be 6 / June", 6, dateTime.getMonthOfYear());
    Assert.assertEquals("Day must be 29 / June", 29, dateTime.getMonthOfYear());
  }

}
