package org.freakz.hokan_ng_springboot.bot.enums;

import org.joda.time.DateTime;

/**
 * Created by Petri Airio on 28.1.2016.
 * -
 */
public enum LunchDay {

  MONDAY("Maanantai", "Monday"),
  TUESDAY("Tiistai", "Tuesday"),
  WEDNESDAY("Keskiviikko", "Wednesday"),
  THURSDAY("Torstai", "Thursday"),
  FRIDAY("Perjantai", "Friday"),
  SATURDAY("Lauantai", "Saturday"),
  SUNDAY("Sunnuntai", "Sunday");

  private final String dayFin;
  private final String dayEng;

  LunchDay(final String dayFin, String dayEng) {
    this.dayFin = dayFin;
    this.dayEng = dayEng;
  }

  public String getDayFin() {
    return dayFin;
  }

  public String getDayEng() {
    return dayEng;
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

  public static LunchDay getFromWeekdayString(String weekday) {
    for (LunchDay lunchDay : values()) {
      if (weekday.toLowerCase().contains(lunchDay.dayFin.toLowerCase())) {
        return lunchDay;
      }
      if (weekday.toLowerCase().contains(lunchDay.dayEng.toLowerCase())) {
        return lunchDay;
      }
    }
    return null;
  }

}
