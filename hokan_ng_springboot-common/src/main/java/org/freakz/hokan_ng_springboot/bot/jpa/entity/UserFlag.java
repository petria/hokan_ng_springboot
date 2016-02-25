package org.freakz.hokan_ng_springboot.bot.jpa.entity;

import lombok.Getter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Petri Airio on 23.2.2016.
 * -
 */
public enum UserFlag implements Serializable {

  ACTIVATED("AC", "User is activated"),
  ADMIN("AD", "User is admin"),
  IGNORE_ON_CHANNEL("IG", "User is ignored on public channels"),
  WEB_LOGIN("WL", "User can login via web ui");

  @Getter
  private final String shortName;
  @Getter
  private final String description;

  UserFlag(String shortName, String description) {
    this.shortName = shortName;
    this.description = description;
  }

  public static Set<UserFlag> getFlagSetFromString(String flagsString) {
    Set<UserFlag> flagsSet = new HashSet<>();
    if (flagsString == null || flagsSet.size() == 0) {
      return flagsSet;
    }
    for (UserFlag flag : values()) {
      if (flagsString.contains(flag.getShortName())) {
        flagsSet.add(flag);
      }
    }
    return flagsSet;
  }

  public static Set<UserFlag> getFlagSetFromUser(User user) {
    return getFlagSetFromString(user.getFlags());
  }

  public static String getStringFromFlagSet(Set<UserFlag> set) {
    String setString = "";
    for (UserFlag flag : values()) {
      if (set.contains(flag)) {
        setString += flag.getShortName() + " ";
      }
    }
    return setString.trim();
  }

  public static String getStringFromFlagSet(User user) {
    return getStringFromFlagSet(getFlagSetFromUser(user));
  }

  public static UserFlag getUserFlagFromString(String s) {
    for (UserFlag flag : values()) {
      if (s.equals(flag.getShortName())) {
        return flag;
      }
      if (s.equals(flag.name())) {
        return flag;
      }
    }
    return null;
  }

}
