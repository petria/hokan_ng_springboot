package org.freakz.hokan_ng_springboot.bot.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Petri Airio on 21.8.2015.
 *
 */
public class DailyStats implements Serializable {


  private String error;
  private Map<String, StatsData> statsDataMap = new HashMap<>();

  public DailyStats() {
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public StatsData getStatsDataForNick(String nick) {
    String nickUpper = nick.toUpperCase();
    StatsData statsData = statsDataMap.get(nickUpper);
    if (statsData == null) {
      statsData = new StatsData(nickUpper);
      statsDataMap.put(nickUpper, statsData);
    }
    return statsData;
  }

}
