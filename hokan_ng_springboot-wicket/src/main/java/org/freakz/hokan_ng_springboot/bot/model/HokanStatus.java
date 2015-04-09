package org.freakz.hokan_ng_springboot.bot.model;

import java.io.Serializable;

/**
 * Created by Petri Airio on 9.4.2015.
 */
public class HokanStatus implements Serializable {

  private String engineStatus;
  private String ioStatus;
  private String servicesStatus;

  public HokanStatus() {
    engineStatus = "<unknown>";
    ioStatus = "<unknown>";
    servicesStatus = "<unknown>";
  }

  public String getEngineStatus() {
    return engineStatus;
  }

  public void setEngineStatus(String engineStatus) {
    this.engineStatus = engineStatus;
  }

  public String getIoStatus() {
    return ioStatus;
  }

  public void setIoStatus(String ioStatus) {
    this.ioStatus = ioStatus;
  }

  public String getServicesStatus() {
    return servicesStatus;
  }

  public void setServicesStatus(String servicesStatus) {
    this.servicesStatus = servicesStatus;
  }
}
