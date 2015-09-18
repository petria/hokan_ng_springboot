package org.freakz.hokan_ng_springboot.bot.command;

/**
 * User: petria
 * Date: 12/12/13
 * Time: 9:49 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
public enum HelpGroup {

  ALIAS("Alias help group"),
  ACCESS_CONTROL("Access control group"),
  CHANNELS("Channels help group"),
  LOGS("IRC logs group"),
  NETWORK("Network help group"),
  PROCESS("Process help group"),
  PROPERTIES("Properties help group"),
  SYSTEM("System help group"),
  TV("TV help group"),
  UPDATERS("Updaters help group"),
  USERS("Users help group"),
  URLS("Urls help group")
  ;

  private String helpText;

  private HelpGroup(String helpText) {
    this.helpText = helpText;
  }

  public String getHelpText() {
    return this.helpText;
  }

}
