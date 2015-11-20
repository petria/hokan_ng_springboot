package org.freakz.hokan_ng_springboot.bot.events;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 26.4.2015.
 *
 */
public enum ServiceRequestType {

  CATCH_URLS_REQUEST("NO_RESPONSE"),
  CURRENCY_CONVERT_REQUEST("CURRENCY_CONVERT_RESPONSE"),
  CURRENCY_LIST_REQUEST("CURRENCY_LIST_RESPONSE"),
  HORO_REQUEST("HORO_DATA_RESPONSE"),
  IMDB_TITLE_REQUEST("IMDB_TITLE_DATA_RESPONSE"),
  METAR_REQUEST("METAR_DATA_RESPONSE"),
  NIMIPAIVA_DAY_REQUEST("NIMIPAIVA_DAY_RESPONSE"),
  NIMIPAIVA_NAME_REQUEST("NIMIPAIVA_NAME_RESPONSE"),
  TRANSLATE_REQUEST("TRANSLATE_RESPONSE"),
  TV_FIND_REQUEST("TV_FIND_DATA_RESPONSE"),
  TV_DAY_REQUEST("TV_DAY_DATA_RESPONSE"),
  TV_INFO_REQUEST("TV_INFO_DATA_RESPONSE"),
  TV_NOW_REQUEST("TV_NOW_DATA_RESPONSE"),
  UPDATERS_LIST_REQUEST("UPDATER_LIST_RESPONSE"),
  UPDATERS_START_REQUEST("UPDATER_START_RESPONSE"),
  WEATHER_REQUEST("WEATHER_DATA_RESPONSE");

  final private String responseDataKey;

  ServiceRequestType(String responseDataKey) {
    this.responseDataKey = responseDataKey;
  }

  public String getResponseDataKey() {
    return responseDataKey;
  }

}
