package org.freakz.hokan_ng_springboot.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.IrcLog;
import org.freakz.hokan_ng_springboot.bot.jpa.service.IrcLogService;
import org.freakz.hokan_ng_springboot.bot.models.StatsData;
import org.freakz.hokan_ng_springboot.bot.models.StatsMapper;
import org.freakz.hokan_ng_springboot.bot.util.StringStuff;
import org.freakz.hokan_ng_springboot.bot.util.TimeUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Petri Airio on 21.8.2015.
 */
@Service
@Slf4j
public class StatsServiceImpl implements StatsService {

  @Autowired
  private IrcLogService ircLogService;

  @Override
  public StatsMapper getDailyStatsForChannel(DateTime day, String channel) {
    List<IrcLog> logs = ircLogService.findByTimeStampBetweenAndTargetContaining(TimeUtil.getStartAndEndTimeForDay(day), channel);
    StatsMapper statsMapper = new StatsMapper();
    if (logs.size() == 0) {
      statsMapper.setError("No stats for day " + StringStuff.formatTime(day.toDate(), StringStuff.STRING_STUFF_DF_DDMMYYYY)
          + " and channel "
          + channel);
    } else {
      processLogs(statsMapper, logs);
    }
    return statsMapper;
  }

  private void processLogs(StatsMapper statsMapper, List<IrcLog> logsForDay) {
    for (IrcLog ircLog : logsForDay) {
      String sender = ircLog.getSender();
      String[] words = ircLog.getMessage().split(" ");
      StatsData statsData = statsMapper.getStatsDataForNick(sender);
      statsData.addToLines(1);
      statsData.addToWords(words.length);
    }
  }

  public StatsMapper getStatsForChannel(String channel) {
    List<IrcLog> logs = ircLogService.findByTarget(channel);
    StatsMapper statsMapper = new StatsMapper();
    if (logs.size() == 0) {
      statsMapper.setError("No stats for channel: " + channel);
    } else {
      processLogs(statsMapper, logs);
    }
    return statsMapper;
  }
}
