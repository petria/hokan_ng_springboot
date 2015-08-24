package org.freakz.hokan_ng_springboot.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.IrcLog;
import org.freakz.hokan_ng_springboot.bot.jpa.service.IrcLogService;
import org.freakz.hokan_ng_springboot.bot.models.DailyStats;
import org.freakz.hokan_ng_springboot.bot.util.TimeUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Petri Airio on 21.8.2015.
 *
 */
@Service
@Slf4j
public class StatsServiceImpl implements StatsService {

  @Autowired
  private IrcLogService ircLogService;

  @Override
  public DailyStats getDailyStats(DateTime day, String channel) {
    List<IrcLog> logsForDay = ircLogService.findByTimeStampBetweenAndTargetContaining(TimeUtil.getStartAndEndTimeForDay(DateTime.now()), channel);
    DailyStats dailyStats = new DailyStats();
    if (logsForDay.size() == 0) {
      dailyStats.setError("No stats for day: " + day.toString());
    } else {
      processLogs(dailyStats, logsForDay);
    }

//    ircLogService.findByTimeStampBetweenAndTargetContaining()
    return dailyStats;
  }

  private void processLogs(DailyStats dailyStats, List<IrcLog> logsForDay) {
    for (IrcLog ircLog : logsForDay) {
      String sender = ircLog.getSender();
      String[] words = ircLog.getMessage().split(" ");
    }
  }

}
