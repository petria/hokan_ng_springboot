package org.freakz.hokan_ng_springboot.bot.jms;

import java.io.Serializable;

/**
 * Created by Petri Airio on 9.4.2015.
 */
public class PingResponse implements Serializable {

  private String reply = "<pong>";

  public PingResponse() {
  }

  public String getReply() {
    return reply;
  }
}
