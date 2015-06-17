package org.freakz.hokan_ng_springboot.bot.updaters;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.cmdpool.CommandPool;
import org.freakz.hokan_ng_springboot.bot.cmdpool.CommandRunnable;
import org.freakz.hokan_ng_springboot.bot.models.DataUpdaterModel;
import org.freakz.hokan_ng_springboot.bot.models.UpdaterStatus;
import org.freakz.hokan_ng_springboot.bot.service.HokanModuleService;
import org.freakz.hokan_ng_springboot.bot.util.StringStuff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * User: petria
 * Date: 11/18/13
 * Time: 8:19 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Service
@Slf4j
public class UpdateManagerServiceImpl implements UpdaterManagerService, CommandRunnable {

  @Autowired
  private ApplicationContext context;

  @Autowired
  private CommandPool commandPool;

  @Autowired
  private HokanModuleService hokanModuleService;


  private Map<String, DataUpdater> handlers;
  private boolean doRun;
  private boolean firstRun = true;

  public UpdateManagerServiceImpl() {
    log.info("START!");

  }

  @PostConstruct
  public void refreshHandlers() {
    handlers = context.getBeansOfType(DataUpdater.class);
    start();
  }


  public void stop() {
    this.doRun = false;
  }

  @Override
  public void start() {
    commandPool.startRunnable(this, "<system>");
  }

  @Override
  public List<DataUpdaterModel> getDataUpdaterModelList() {
    List<DataUpdaterModel> modelList = new ArrayList<>();
    for (DataUpdater updater : this.handlers.values()) {
      DataUpdaterModel model = new DataUpdaterModel();
      model.setName(updater.getUpdaterName());
      model.setStatus(updater.getStatus());
      model.setCount(updater.getUpdateCount());
      model.setNextUpdate(updater.getNextUpdateTime().getTime());
      model.setLastUpdate(updater.getLastUpdateTime().getTime());
      modelList.add(model);
    }
    return modelList;
  }

  @Override
  public List<DataUpdater> getUpdaterList() {
    return new ArrayList<>(this.handlers.values());
  }

  public DataUpdater getUpdater(String updaterName) {
    for (DataUpdater updater : handlers.values()) {
      if (StringStuff.match(updater.getUpdaterName(), updaterName, true)) {
        return updater;
      }
    }
    return null;
  }

  @Override
  public void startUpdater(DataUpdater updater) {
    log.info("Starting updater: " + updater);
    updater.updateData(this.commandPool);
  }

  @Override
  public void handleRun(long myPid, Object args) {
    doRun = true;
    log.info("<< Starting update service: {} >>", myPid);
    while (doRun) {
//      log.info("ping");
      for (DataUpdater updater : getUpdaterList()) {
        Calendar now = new GregorianCalendar();
        Calendar next = updater.getNextUpdateTime();
        if (firstRun || now.after(next)) {
          if (updater.getStatus() == UpdaterStatus.CRASHED) {
            continue;
          }
          if (updater.getStatus() != UpdaterStatus.UPDATING) {
            updater.updateData(this.commandPool);
          }
        }
      }
      firstRun = false;
      try {
        Thread.sleep(1000 * 10);
      } catch (InterruptedException e) {
        // ignore
      }
    }
    log.info("<< Stoping update service: {} >>", myPid);
  }
}
