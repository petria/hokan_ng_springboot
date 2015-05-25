package org.freakz.hokan_ng_springboot.bot.events;

import java.io.Serializable;

/**
 * Created by Petri Airio on 25.5.2015.
 *
 */
public class TvNotifyRequest implements Serializable {

  private long targetChannelId;
  private String notifyMessage;

  public TvNotifyRequest() {
  }

  public long getTargetChannelId() {
    return targetChannelId;
  }

  public String getNotifyMessage() {
    return notifyMessage;
  }

  public void setTargetChannelId(long id) {
    this.targetChannelId = id;
  }

  public void setNotifyMessage(String notifyMessage) {
    this.notifyMessage = notifyMessage;
  }

}
