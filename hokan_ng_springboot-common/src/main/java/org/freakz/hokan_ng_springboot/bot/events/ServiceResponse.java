package org.freakz.hokan_ng_springboot.bot.events;

import org.freakz.hokan_ng_springboot.bot.models.HoroHolder;
import org.freakz.hokan_ng_springboot.bot.models.MetarData;
import org.freakz.hokan_ng_springboot.bot.models.WeatherData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 24.4.2015.
 *
 */
public class ServiceResponse implements Serializable {

  private final Map<String, Object> responseData = new HashMap<>();

  public ServiceResponse() {
  }

  public void setResponseData(String key, Object data) {
    responseData.put(key, data);
  }

  public Object getResponseData(String key) {
    return responseData.get(key);
  }

  public List<MetarData> getMetarResponse() {
    List<MetarData> dataList = (List<MetarData>) responseData.get("METAR_DATA");
    if (dataList == null) {
      return new ArrayList<>();
    }
    return dataList;
  }

  public HoroHolder getHoroResponse() {
    HoroHolder hh = (HoroHolder) responseData.get("HORO_DATA");
    return hh;
  }

  public List<WeatherData> getWeatherResponse() {
    List<WeatherData> dataList = (List<WeatherData>) responseData.get("WEATHER_DATA");
    if (dataList == null) {
      return new ArrayList<>();
    }
    return dataList;
  }

}
