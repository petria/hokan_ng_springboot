package org.freakz.hokan_ng_springboot.bot.events;

import org.freakz.hokan_ng_springboot.bot.models.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 24.4.2015.
 *
 */
@SuppressWarnings("unchecked")
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

  public List<KelikameratWeatherData> getWeatherResponse() {
    List<KelikameratWeatherData> data = (List<KelikameratWeatherData>) responseData.get("WEATHER_DATA");
    if (data == null) {
      return new ArrayList<>();
    }
    return data;
  }

  public List<TelkkuProgram> getTvFindData() {
    List<TelkkuProgram> data = (List<TelkkuProgram>) responseData.get("TV_FIND_DATA");
    if (data == null) {
      return new ArrayList<>();
    }
    return data;
  }

  public TvNowData getTvNowData() {
    TvNowData data = (TvNowData) responseData.get("TV_NOW_DATA");
    if (data == null) {
      return new TvNowData();
    }
    return data;
  }

  public TelkkuProgram getTvInfoData() {
    return (TelkkuProgram) responseData.get("TV_INFO_DATA");
  }

  public List<TelkkuProgram> getTvDayData() {
    List<TelkkuProgram> data = (List<TelkkuProgram>) responseData.get("TV_DAY_DATA");
    if (data == null) {
      return new ArrayList<>();
    }
    return data;
  }

  public List<DataUpdaterModel> getUpdaterListData() {
    List<DataUpdaterModel> data = (List<DataUpdaterModel>) responseData.get("UPDATER_LIST_RESPONSE");
    if (data == null) {
      return new ArrayList<>();
    }
    return data;
  }

  public List<DataUpdaterModel> getStartUpdaterListData() {
    List<DataUpdaterModel> data = (List<DataUpdaterModel>) responseData.get("START_UPDATER_LIST_RESPONSE");
    if (data == null) {
      return new ArrayList<>();
    }
    return data;
  }

  public String getCurrencyConvertResponse() {
    return (String) responseData.get("CURRENCY_CONVERT_RESPONSE");
  }

  public List<GoogleCurrency> getCurrencyListResponse() {
    return (List<GoogleCurrency>) responseData.get("CURRENCY_LIST_RESPONSE");
  }

  public NimipaivaData getNimipaivaDayResponse() {
    return (NimipaivaData) responseData.get("NIMIPAIVA_DAY_RESPONSE");

  }

  public NimipaivaData getNimipaivaNameResponse() {
    return (NimipaivaData) responseData.get("NIMIPAIVA_NAME_RESPONSE");
  }

  public TranslateResponse getTranslateResponse() {
    return (TranslateResponse) responseData.get("TRANSLATE_RESPONSE");
  }

  public IMDBSearchResults getIMDBTitleData() {
   return (IMDBSearchResults) responseData.get("IMDB_TITLE_DATA");
  }

}
