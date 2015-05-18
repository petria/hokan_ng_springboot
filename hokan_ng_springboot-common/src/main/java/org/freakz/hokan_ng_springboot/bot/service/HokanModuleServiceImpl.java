package org.freakz.hokan_ng_springboot.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.enums.HokanModule;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.CommandHistory;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.CommandStatus;
import org.freakz.hokan_ng_springboot.bot.jpa.service.CommandHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Petri Airio on 15.5.2015.
 *
 */
@Service
@Scope("singleton")
@Slf4j
public class HokanModuleServiceImpl implements HokanModuleService {

  @Autowired
  private CommandHistoryService commandHistoryService;

  private HokanModule module;

  @Override
  public void setHokanModule(HokanModule module) {
    this.module = module;
    log.info("Module set to {}", module.toString());
    List<CommandHistory> historyList = commandHistoryService.findByHokanModule(module.toString());
    for (CommandHistory history : historyList) {
      if (history.getEndTime() == null) {
        history.setEndTime(new Date());
        history.setStatus(CommandStatus.FINISHED);
        commandHistoryService.save(history);
      }
    }
  }

  @Override
  public HokanModule getHokanModule() {
    return this.module;
  }

}
