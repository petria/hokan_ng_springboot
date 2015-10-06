package org.freakz.hokan_ng_springboot.bot.util;

import org.freakz.hokan_ng_springboot.bot.models.StartAndEndTime;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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

  public static DateTime parseDateTime(String string) {
    DateTime parsed = null;
    DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.");
    try {
      parsed = formatter.parseDateTime(string);
      return parsed;
    } catch (IllegalArgumentException e) {
      //
    }
    formatter = DateTimeFormat.forPattern("dd.MM");
    try {
      parsed = formatter.parseDateTime(string);
      return parsed;
    } catch (IllegalArgumentException e) {
      //
    }

    return parsed;
  }


}
