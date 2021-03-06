package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPResult;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.command.HelpGroup;
import org.freakz.hokan_ng_springboot.bot.command.annotation.HelpGroups;
import org.freakz.hokan_ng_springboot.bot.enums.HokanModule;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.CommandHistory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.freakz.hokan_ng_springboot.bot.util.StaticStrings.ARG_COUNT;

/**
 * Created by Petri Airio on 19.5.2015.
 *
 */
@Component
@Scope("prototype")
@Slf4j
@HelpGroups(
    helpGroups = {HelpGroup.PROCESS}
)
public class LastcommCmd extends Cmd {

  public LastcommCmd() {
    super();
    setHelp("Shows executed processes in Bot.");

    FlaggedOption flg = new FlaggedOption(ARG_COUNT)
        .setStringParser(JSAP.INTEGER_PARSER)
        .setDefault("5")
        .setShortFlag('c');
    registerParameter(flg);
  }

  @Override
  public void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) throws HokanException {
    List<CommandHistory> all = new ArrayList<>();
    for (HokanModule module : HokanModule.values()) {
      long sessionId = propertyService.getPropertyAsLong(module.getModuleProperty(), -1);
      if (sessionId != -1) {
        List<CommandHistory> running = commandHistoryService.findByHokanModuleAndSessionId(module.toString(), sessionId);
        all.addAll(running);
      }
    }
    if (all.size() > 0) {
      Comparator<CommandHistory> comparator = (o1, o2) -> o2.getStartTime().compareTo(o1.getStartTime());
      Collections.sort(all, comparator);
      int max = results.getInt(ARG_COUNT);
      int count = 0;
      response.addResponse("%2s - %10s - %-13s - %s\n", "PID", "STARTED_BY", "START_TIME", "CLASS");
      for (CommandHistory cmd : all) {
        response.addResponse("%2d - %10s - %-13s - %s\n", cmd.getPid(), cmd.getStartedBy(), cmd.getStartTime(), cmd.getRunnable().replaceAll("class org.freakz.hokan_ng_springboot.bot.", ""));
        count++;
        if (count == max) {
          break;
        }
      }
    }

  }
}
