package org.freakz.hokan_ng_springboot.bot.models;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Petri Airio on 6.10.2015.
 *
 */
public class NimipaivaData implements Serializable {

  private DateTime day;
  private List<String> names;

  public NimipaivaData() {
  }

  public NimipaivaData(DateTime day, List<String> names) {
    this.day = day;
    this.names = names;
  }


  public DateTime getDay() {
    return day;
  }

  public void setDay(DateTime day) {
    this.day = day;
  }

  public List<String> getNames() {
    return names;
  }

  public void setNames(List<String> names) {
    this.names = names;
  }
}
