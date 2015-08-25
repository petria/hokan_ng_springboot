package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.JSAPResult;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.models.DailyStats;
import org.freakz.hokan_ng_springboot.bot.models.StatsData;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Petri Airio on 24.8.2015.
 *
 */
@Component
@Scope("prototype")
@Slf4j
public class DailyStatsCmd extends Cmd {

  public DailyStatsCmd() {
    super();
    setHelp("Show daily stats: lines / words written to specific channel.");
  }

  @Override
  public void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) throws HokanException {
    DateTime today = DateTime.now().minusDays(4);
    DailyStats dailyStats = statsService.getDailyStats(today, request.getChannel().getChannelName());
    if (!dailyStats.hasError()) {
      List<StatsData> statsDatas = dailyStats.getStatsData();
      String res = "Top words:";
      int i = 1;
      for (StatsData statsData : statsDatas) {

        res += " " + i + ") " + statsData.getNick() + ": " + statsData.getWords();
        i++;
      }
      response.addResponse(res);
    }
  }

}
