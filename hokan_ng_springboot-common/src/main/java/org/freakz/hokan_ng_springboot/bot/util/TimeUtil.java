package org.freakz.hokan_ng_springboot.bot.util;

import org.freakz.hokan_ng_springboot.bot.models.StartAndEndTime;
import org.joda.time.DateTime;

/**
 * Created by Petri Airio on 21.8.2015.
 *
 */
public class TimeUtil {

  public static StartAndEndTime getStartAndEndTimeForDay(DateTime day) {
    DateTime todayStart = day.withTimeAtStartOfDay();
    DateTime tomorrowStart = day.plusDays(1).withTimeAtStartOfDay();
    return new StartAndEndTime(todayStart, tomorrowStart);
  }


}
