package org.freakz.hokan_ng_springboot.bot.updaters;


import org.freakz.hokan_ng_springboot.bot.cmdpool.CommandPool;

import java.util.Calendar;

/**
 * User: petria
 * Date: 11/18/13
 * Time: 2:25 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
public interface DataUpdater {

  Calendar getNextUpdateTime();

  Calendar getLastUpdateTime();

  Calendar calculateNextUpdate();

  void updateData(CommandPool commandPool);

  void getData(UpdaterData updaterData, String... args);

  UpdaterData getData(String... args);

  UpdaterStatus getStatus();

  long getUpdateCount();

  String getUpdaterName();


}
