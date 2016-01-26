package org.freakz.hokan_ng_springboot.bot.enums;

/**
 * Created by Petri Airio on 21.1.2016.
 *
 */
public enum LunchPlace {

  LOUNAS_INFO_JYSKÄ("Landis+Gyr", "http://www.lounasinfo.fi/index.php?c=Suomi&t=Jyv%E4skyl%E4&a=Jysk%E4&r=35"),
  LOUNAS_INFO_HARMOONI("Harmooni", "http://www.harmooni.fi/ravintola/lounas/");

  private final String url;
  private final String name;

  LunchPlace(String name, String url) {
    this.name = name;
    this.url = url;
  }

  public String getName() {
    return name;
  }

  public String getUrl() {
    return url;
  }

}
