package org.freakz.hokan_ng_springboot.bot.model;

import org.freakz.hokan_ng_springboot.bot.jms.PingResponse;

import java.io.Serializable;

/**
 * Created by Petri Airio on 9.4.2015.
 *
 */
public class HokanStatusModel implements Serializable {

  private String status;
  private PingResponse pingResponse;

  public HokanStatusModel(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public PingResponse getPingResponse() {
    return pingResponse;
  }

  public void setPingResponse(PingResponse pingResponse) {
    this.pingResponse = pingResponse;
  }
}
