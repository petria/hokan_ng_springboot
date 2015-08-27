package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.UnflaggedOption;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.IrcLog;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.freakz.hokan_ng_springboot.bot.util.StaticStrings.ARG_LOG_PATTERN;

/**
 * Created by Petri Airio on 21.8.2015.
 *
 */
@Component
@Scope("prototype")
@Slf4j
public class FindLogCmd extends Cmd {

  private static final int SHOW_MAX = 3;

  public FindLogCmd() {
    super();
    setHelp("Finds rows matching logs.");
    addToHelpGroup(HelpGroup.LOGS, this);

    UnflaggedOption flg = new UnflaggedOption(ARG_LOG_PATTERN)
        .setRequired(true)
        .setGreedy(false);
    registerParameter(flg);

  }

  @Override
  public void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) throws HokanException {
    String logPattern = results.getString(ARG_LOG_PATTERN);
    List<IrcLog> ircLogs = ircLogService.findMatchingLogRows(logPattern);
//    List<IrcLog> test = ircLogService.findByTimeStampBetweenAndTarget(TimeUtil.getStartAndEndTimeForDay(DateTime.now()), "FFFuf");

    if (ircLogs.size() > 0) {
      int max = SHOW_MAX;
      for (IrcLog log : ircLogs) {
        response.addResponse("LOG: %s\n", log.toString());
        max--;
        if (max == 0) {
          if (ircLogs.size() - SHOW_MAX > 0) {
            response.addResponse("... %d matching rows more", ircLogs.size() - SHOW_MAX);
          }
          break;
        }
      }
    } else {
      response.addResponse("LOG: nothing found with %s\n", logPattern);
    }
  }

}
