package org.freakz.hokan_ng_springboot.bot.enums;

/**
 * Created by Petri Airio on 13.11.2015.
 * -
 */
public enum CommandLineArgs {

  JMS_BROKER_URL("--JmsBrokerUrl");

  private String commandLineArg;

  CommandLineArgs(String ommandLineArg) {
    this.commandLineArg = ommandLineArg;
  }

  public String getCommandLineArg() {
    return this.commandLineArg;
  }


}
