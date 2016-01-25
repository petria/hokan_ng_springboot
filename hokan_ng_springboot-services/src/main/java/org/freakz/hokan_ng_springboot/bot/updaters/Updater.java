package org.freakz.hokan_ng_springboot.bot.updaters;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.cmdpool.CommandPool;
import org.freakz.hokan_ng_springboot.bot.cmdpool.CommandRunnable;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.models.UpdaterStatus;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * User: petria
 * Date: 11/18/13
 * Time: 2:31 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Component
@Slf4j
public abstract class Updater implements DataUpdater, CommandRunnable {

  protected long updateCount = 0;

  protected long dataFetched = 0;
  protected long itemsFetched = 0;
  protected long itemCount = 0;

  protected long lastUpdateRuntime = 0;
  protected long totalUpdateRuntime = 0;

  private Calendar nextUpdate = new GregorianCalendar();
  private Calendar lastUpdate;
  protected UpdaterStatus status;

  protected Updater() {
  }

  @Override
  public String getUpdaterName() {
    return this.getClass().getSimpleName();
  }

  @Override
  public Calendar getNextUpdateTime() {
    return nextUpdate;
  }

  @Override
  public Calendar getLastUpdateTime() {
    return lastUpdate;
  }

  @Override
  public Calendar calculateNextUpdate() {
    Calendar cal = new GregorianCalendar();
    cal.add(Calendar.MINUTE, 5);
    return cal;
  }

  @Override
  public void updateData(CommandPool commandPool) {
    commandPool.startRunnable(this, "<system>");
  }

  @Override
  public void handleRun(long myPid, Object args) throws HokanException {
    try {
      status = UpdaterStatus.UPDATING;
      long startTime = System.currentTimeMillis();
      doUpdateData();
      long duration = (startTime - System.currentTimeMillis()) / 1000;
      this.lastUpdateRuntime = duration;
      this.totalUpdateRuntime += duration;
      status = UpdaterStatus.IDLE;

    } catch (Exception e) {
      log.error("Updater failed", e);
      status = UpdaterStatus.CRASHED;
      throw new HokanException("Updater failed", e);
    } finally {
      updateCount++;
      lastUpdate = new GregorianCalendar();
    }
    nextUpdate = calculateNextUpdate();
  }


  public abstract void doUpdateData() throws Exception;

  @Override
  public void getData(UpdaterData data, Object... args) {
    data.setData(doGetData(args));
  }

  @Override
  public UpdaterData getData(Object... args) {
    UpdaterData data = new UpdaterData();
    data.setData(doGetData(args));
    return data;
  }

  public abstract Object doGetData(Object... args);

  @Override
  public UpdaterStatus getStatus() {
    return this.status;
  }

  @Override
  public long getUpdateCount() {
    return this.updateCount;
  }

  @Override
  public long getDataFetched() {
    return this.dataFetched;
  }

  @Override
  public long getItemsFetched() {
    return this.itemsFetched;
  }

  @Override
  public long getItemmCount() {
    return this.itemCount;
  }

  @Override
  public long getLastUpdateRuntime() {
    return this.lastUpdateRuntime;
  }

  @Override
  public long getTotalUpdateRuntime() {
    return this.totalUpdateRuntime;
  }

}
