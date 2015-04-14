package org.freakz.hokan_ng_springboot.bot.jms;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by petria on 4.2.2015.
 */
public class JmsMessage implements Serializable {

  private Map<String, Object> payload = new HashMap<>();

  public Map<String, Object> getPayload() {
    return payload;
  }

  public void setPayload(Map<String, Object> payload) {
    this.payload = payload;
  }

  public void clearPayload() {
    this.payload = new HashMap<>();
  }

  public void addPayLoadObject(String key, Object data) {
    this.payload.put(key, data);
  }

  public Object getPayLoadObject(String key) {
    return this.payload.get(key);
  }

  public String getCommand() {
    String command = (String) getPayLoadObject("COMMAND");
    if (command == null) {
      return "<null>";
    }
    return command;
  }

  public String toString() {
    String values = "";
    for (String key : payload.keySet()) {
      values += " ";
      values += key + "=" + payload.get(key);
    }
    return this.getClass() + " :: ";

  }
}
