package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.JSAPResult;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.enums.HokanModule;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.CommandHistory;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.CommandStatus;
import org.freakz.hokan_ng_springboot.bot.jpa.service.CommandHistoryService;
import org.freakz.hokan_ng_springboot.bot.jpa.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petri Airio on 18.5.2015.
 *
 */
@Component
@Scope("prototype")
@Slf4j
public class PsCmd extends Cmd {

  @Autowired
  private PropertyService propertyService;

  @Autowired
  private CommandHistoryService commandHistoryService;

  public PsCmd() {
    super();
    setHelp("Shows active processes running in Bot.");
    addToHelpGroup(HelpGroup.PROCESS, this);
  }

  @Override
  public void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) throws HokanException {
    List<CommandHistory> allRunning = new ArrayList<>();
    for (HokanModule module : HokanModule.values()) {
      long sessionId = propertyService.getPropertyAsLong(module.getModuleProperty(), -1);
      if (sessionId != -1) {
        List<CommandHistory> running = commandHistoryService.findByHokanModuleAndSessionIdAndCommandStatus(module.toString(), sessionId, CommandStatus.RUNNING);
        allRunning.addAll(running);
      }
    }
    if (allRunning.size() > 0) {
      response.addResponse("%2s - %-13s - %s\n", "PID", "MODULE", "CLASS");
      for (CommandHistory cmd : allRunning) {
        response.addResponse("%2d - %-13s - %s\n", cmd.getPid(), cmd.getHokanModule(), cmd.getRunnable().replaceAll("class org.freakz.hokan_ng_springboot.bot.", ""));
      }
    }

  }

}
