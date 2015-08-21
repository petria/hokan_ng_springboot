package org.freakz.hokan_ng_springboot.bot.util;

import org.freakz.hokan_ng_springboot.bot.models.StartAndEndTime;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Petri Airio on 21.8.2015.
 *
 */
public class TimeUtilTest {

  @Test
  public void testTimeUtil() {
    DateTime now = DateTime.now();
    StartAndEndTime test = TimeUtil.getStartAndEndTimeForDay(now);
    Assert.assertTrue(true);
  }

}
