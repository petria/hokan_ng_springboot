package org.freakz.hokan_ng_springboot.bot.command;

/**
 * User: petria
 * Date: 12/12/13
 * Time: 9:49 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
public enum HelpGroup {

  ALIAS("Aliases", ""),
  ACCESS_CONTROL("Access control", ""),
  CHANNELS("Channels", ""),
  DATA_COLLECTION("Data collection", ""),
  DATA_FETCHERS("Data fetchers", ""),
  HELP("Help", ""),
  LOGS("Logs", ""),
  LUNCH("Lunch", "Lunch menu fetchers"),
  NETWORK("Network", ""),
  PROCESS("Process", ""),
  PROPERTIES("Properties", ""),
  SYSTEM("System", ""),
  TV("TV", ""),
  UPDATERS("Updaters", ""),
  USERS("Users", ""),
  URLS("Urls", ""),
  UTILITY("Utility", "");

  private String groupName;
  private String groupExplanation;

  HelpGroup(String groupName, String groupExplanation) {
    this.groupName = groupName;
    this.groupExplanation = groupExplanation;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public String getGroupExplanation() {
    return groupExplanation;
  }

  public void setGroupExplanation(String groupExplanation) {
    this.groupExplanation = groupExplanation;
  }
}
