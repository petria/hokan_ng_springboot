package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPResult;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.enums.HokanModule;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.CommandHistory;
import org.freakz.hokan_ng_springboot.bot.jpa.service.CommandHistoryService;
import org.freakz.hokan_ng_springboot.bot.jpa.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class LastcommCmd extends Cmd {

  @Autowired
  private PropertyService propertyService;

  @Autowired
  private CommandHistoryService commandHistoryService;


  public LastcommCmd() {
    super();
    setHelp("Shows executed processes in Bot.");
    addToHelpGroup(HelpGroup.PROCESS, this);

    FlaggedOption flg = new FlaggedOption(ARG_COUNT)
        .setStringParser(JSAP.INTEGER_PARSER)
        .setDefault("5")
        .setShortFlag('c');
    registerParameter(flg);

    /*
    TODO add limeters to narrow query...
     */
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
      Comparator<CommandHistory> comparator = new Comparator<CommandHistory>() {
        @Override
        public int compare(CommandHistory o1, CommandHistory o2) {
          return o1.getStartTime().compareTo(o2.getStartTime());
        }
      };
      Collections.sort(all, comparator);

      response.addResponse("%2s - %10s - %-13s - %s\n", "PID", "STARTED_BY", "START_TIME", "CLASS");
      for (CommandHistory cmd : all) {
        response.addResponse("%2d - %10s - %-13s - %s\n", cmd.getPid(), cmd.getStartedBy(), cmd.getStartTime(), cmd.getRunnable().replaceAll("class org.freakz.hokan_ng_springboot.bot.", ""));
      }
    }

  }
}
