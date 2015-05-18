package org.freakz.hokan_ng_springboot.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.enums.HokanModule;
import org.freakz.hokan_ng_springboot.bot.jpa.service.CommandHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;

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

  private long sessionId;

  private HokanModule module;

  @Override
  public void setHokanModule(HokanModule module) {
    this.module = module;
    this.sessionId = new Date().getTime();
    log.info("Module set to {}", module.toString());
/*    List<CommandHistory> historyList = commandHistoryService.findByHokanModule(module.toString());
    for (CommandHistory history : historyList) {
      if (history.getEndTime() == null && history.getStatus() == CommandStatus.RUNNING) {
        history.setEndTime(new Date());
        history.setStatus(CommandStatus.INTERRUPTED);
        commandHistoryService.save(history);
      }
    }*/
  }

  @Override
  public HokanModule getHokanModule() {
    return this.module;
  }

  @Override
  public long getSessionId() {
    return this.sessionId;
  }

}
