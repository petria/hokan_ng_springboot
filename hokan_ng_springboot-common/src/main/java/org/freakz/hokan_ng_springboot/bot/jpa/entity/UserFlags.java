package org.freakz.hokan_ng_springboot.bot.jpa.entity;

import lombok.Getter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Petri Airio on 23.2.2016.
 * -
 */
public enum UserFlags implements Serializable {

  ACTIVATED("AC", "User is activated"),
  ADMIN("AD", "User is admin"),
  IGNORE_ON_CHANNEL("IG", "User is ignored on public channels"),
  WEB_LOGIN("WL", "User can login via web ui");

  @Getter
  private final String shortName;
  @Getter
  private final String description;

  UserFlags(String shortName, String description) {
    this.shortName = shortName;
    this.description = description;
  }

  public static Set<UserFlags> getFlagSetFromString(String flagsString) {
    Set<UserFlags> flagsSet = new HashSet<>();
    if (flagsString == null || flagsSet.size() == 0) {
      return flagsSet;
    }
    for (UserFlags flag : values()) {
      if (flagsString.contains(flag.getShortName())) {
        flagsSet.add(flag);
      }
    }
    return flagsSet;
  }

  public static Set<UserFlags> getFlagSetFromUser(User user) {
    return getFlagSetFromString(user.getFlags());
  }


}
