package org.freakz.hokan_ng_springboot.bot.enums;

/**
 * Created by Petri Airio on 21.1.2016.
 *
 */
public enum LunchPlace {

  LOUNAS_INFO_JYSKÄ("http://www.lounasinfo.fi/index.php?c=Suomi&t=Jyv%E4skyl%E4&a=Jysk%E4&r=35");

  private final String url;

  LunchPlace(String url) {
    this.url = url;
  }

}
