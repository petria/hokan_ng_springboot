package org.freakz.hokan_ng_springboot.bot.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Petri Airio on 17.6.2015.
 *
 */
public class DataUpdaterModel implements Serializable {

  private String name;
  private UpdaterStatus status;
  private long count;
  private Date nextUpdate;
  private Date LastUpdate;

  public DataUpdaterModel() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UpdaterStatus getStatus() {
    return status;
  }

  public void setStatus(UpdaterStatus status) {
    this.status = status;
  }

  public long getCount() {
    return count;
  }

  public void setCount(long count) {
    this.count = count;
  }

  public Date getNextUpdate() {
    return nextUpdate;
  }

  public void setNextUpdate(Date nextUpdate) {
    this.nextUpdate = nextUpdate;
  }

  public Date getLastUpdate() {
    return LastUpdate;
  }

  public void setLastUpdate(Date lastUpdate) {
    LastUpdate = lastUpdate;
  }
}
