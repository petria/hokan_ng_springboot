package org.freakz.hokan_ng_springboot.bot.models;

import org.joda.time.DateTime;

/**
 * Created by Petri Airio on 21.8.2015.
 *
 */
public class StartAndEndTime {

  private DateTime startTime;
  private DateTime endTime;

  public StartAndEndTime(DateTime startTime, DateTime endTime) {
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public DateTime getStartTime() {
    return startTime;
  }

  public void setStartTime(DateTime startTime) {
    this.startTime = startTime;
  }

  public DateTime getEndTime() {
    return endTime;
  }

  public void setEndTime(DateTime endTime) {
    this.endTime = endTime;
  }
}
