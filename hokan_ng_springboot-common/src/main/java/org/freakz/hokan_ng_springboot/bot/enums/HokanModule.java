package org.freakz.hokan_ng_springboot.bot.enums;

/**
 * Created by Petri Airio on 9.4.2015.
 *
 */
public enum HokanModule {

  HokanIo("HokanNGIoQueue"),
  HokanEngine("HokanNGEngineQueue"),
  HokanServices("HokanNGServicesQueue"),
  HokanWicket("HokanNGWicketQueue");

  private final String queueName;

  HokanModule(String queueName) {
    this.queueName = queueName;
  }

  public String getQueueName() {
    return queueName;
  }

}
