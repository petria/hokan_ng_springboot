package org.freakz.hokan_ng_springboot.bot.models;

import org.freakz.hokan_ng_springboot.bot.enums.LunchDay;
import org.freakz.hokan_ng_springboot.bot.enums.LunchPlace;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Petri Airio on 21.1.2016.
 * -
 */
public class LunchData implements Serializable {

  private LunchPlace lunchPlace;
  private Map<LunchDay, LunchMenu> menu = new HashMap<>();

  public LunchData() {
  }

  public LunchPlace getLunchPlace() {
    return lunchPlace;
  }

  public void setLunchPlace(LunchPlace lunchPlace) {
    this.lunchPlace = lunchPlace;
  }

  public Map<LunchDay, LunchMenu> getMenu() {
    return menu;
  }

  @Override
  public String toString() {
    return lunchPlace.getName() + " :: " + menu;
  }

}
