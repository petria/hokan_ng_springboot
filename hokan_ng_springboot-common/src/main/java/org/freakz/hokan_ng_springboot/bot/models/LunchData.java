package org.freakz.hokan_ng_springboot.bot.models;

import org.freakz.hokan_ng_springboot.bot.enums.LunchPlace;
import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Created by Petri Airio on 21.1.2016.
 * -
 */
public class LunchData implements Serializable {

  private DateTime day;
  private LunchPlace lunchPlace;
  private String lunchMenu;

  public LunchData() {
  }

  public DateTime getDay() {
    return day;
  }

  public void setDay(DateTime day) {
    this.day = day;
  }

  public LunchPlace getLunchPlace() {
    return lunchPlace;
  }

  public void setLunchPlace(LunchPlace lunchPlace) {
    this.lunchPlace = lunchPlace;
  }

  public String getLunchMenu() {
    return lunchMenu;
  }

  public void setLunchMenu(String lunchMenu) {
    this.lunchMenu = lunchMenu;
  }

  @Override
  public String toString() {
    return lunchPlace.getName() + " :: " + lunchMenu;
  }

}
