package org.freakz.hokan_ng_springboot.bot.enums;

/**
 * Created by Petri Airio on 21.1.2016.
 *
 */
public enum LunchPlace {

  LOUNAS_INFO_HARMOONI("Harmooni", "https://www.harmooni.fi/ravintola/lounas/"),
  LOUNAS_INFO_HERKKUPISTE("Herkkupiste", "http://www.herkkupiste.fi/lounaslista.html"),
  LOUNAS_INFO_HKI_TERMINAALI2("Terminaali2", "http://www.sspfinland.fi/fi/helsinki-vantaan-lentoaseman-ravintolat-kahvilat-ja-lounget/terminaali-2/ravintolat/cesars-pizza-and-food-court/"),
  LOUNAS_INFO_QULKURI("Qulkuri", "http://www.qulkuri.fi/");

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

  public static LunchPlace getLunchPlace(String argLunchPlace) {
    for (LunchPlace lunchPlace : values()) {
      if (lunchPlace.getName().toLowerCase().contains(argLunchPlace.toLowerCase())) {
        return lunchPlace;
      }
    }
    return null;
  }

}
