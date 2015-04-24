package org.freakz.hokan_ng_springboot.bot.events;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 24.4.2015.
 *
 */
public class ServiceResponse implements Serializable {

  private final Map<String, Object> responseData = new HashMap<>();

  public ServiceResponse() {
  }

  public void setResponseData(String key, Object data) {
    responseData.put(key, data);
  }

  public Object getResponseData(String key) {
    return responseData.get(key);
  }

}
