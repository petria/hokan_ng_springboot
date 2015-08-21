package org.freakz.hokan_ng_springboot.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jpa.service.IrcLogService;
import org.freakz.hokan_ng_springboot.bot.models.DailyStats;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
  public DailyStats getDailyStats(DateTime day) {
    return null;
  }

}
