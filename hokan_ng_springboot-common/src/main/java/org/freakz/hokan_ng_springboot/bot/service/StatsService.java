package org.freakz.hokan_ng_springboot.bot.service;

import org.freakz.hokan_ng_springboot.bot.models.StatsMapper;
import org.joda.time.DateTime;

/**
 * Created by Petri Airio on 21.8.2015.
 *
 */
public interface StatsService {

  StatsMapper getDailyStatsForChannel(DateTime day, String channel);

  StatsMapper getStatsForChannel(String channel);

}
