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

  private final ServiceRequestType type;
  private final IrcMessageEvent ircMessageEvent;
  private final String[] parameters;

  public ServiceRequest(ServiceRequestType type, IrcMessageEvent ircMessageEvent, String... parameters) {
    this.type = type;
    this.ircMessageEvent = ircMessageEvent;
    this.parameters = parameters;
  }

  public ServiceRequestType getType() {
    return type;
  }

  public IrcMessageEvent getIrcMessageEvent() {
    return ircMessageEvent;
  }

  public String[] getParameters() {
    return parameters;
  }

}
