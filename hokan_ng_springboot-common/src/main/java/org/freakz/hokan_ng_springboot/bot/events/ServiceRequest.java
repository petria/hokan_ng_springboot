package org.freakz.hokan_ng_springboot.bot.events;

import java.io.Serializable;

/**
 * Created by Petri Airio on 22.4.2015.
 *
 */
public class ServiceRequest implements Serializable {

  public enum ServiceRequestType {
    METAR_REQUEST
  }

  public ServiceRequest() {
  }


}
