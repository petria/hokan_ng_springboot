package org.freakz.hokan_ng_springboot.bot.enums;

import org.joda.time.DateTime;

/**
 * Created by Petri Airio on 28.1.2016.
 * -
 */
public enum LunchDay {

  MONDAY("Maanantai"),
  TUESDAY("Tiistai"),
  WEDNESDAY("Keskiviikko"),
  THURSDAY("Torstai"),
  FRIDAY("Perjantai");

  private final String day;

  LunchDay(final String day) {
    this.day = day;
  }

  public String getDay() {
    return day;
  }

  public static LunchDay getFromDateTime(DateTime day) {
    switch (day.getDayOfWeek()) {
      case 1:
        return LunchDay.MONDAY;
      case 2:
        return LunchDay.TUESDAY;
      case 3:
        return LunchDay.WEDNESDAY;
      case 4:
        return LunchDay.THURSDAY;
      case 5:
        return LunchDay.FRIDAY;
    }
    return null;
  }

}
