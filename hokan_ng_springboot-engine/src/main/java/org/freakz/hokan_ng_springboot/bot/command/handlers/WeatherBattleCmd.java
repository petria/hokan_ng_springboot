package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.UnflaggedOption;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.events.ServiceRequestType;
import org.freakz.hokan_ng_springboot.bot.events.ServiceResponse;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.models.KelikameratWeatherData;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.freakz.hokan_ng_springboot.bot.util.StaticStrings.ARG_PLACE;

/**
 * Created by Petri Airio on 17.6.2015.
 *
 */
@Component
@Scope("prototype")
public class WeatherBattleCmd extends Cmd {

  private final static String FORMAT = "%1 %2 %3°C (%7/%8)";

  public WeatherBattleCmd() {
    super();
    setHelp("Weather battle.");

    UnflaggedOption opt = new UnflaggedOption(ARG_PLACE)
        .setDefault("Jyväskylä Turku")
        .setRequired(true)
        .setGreedy(true);
    registerParameter(opt);

  }

  private String formatWeather(KelikameratWeatherData d) {
    return String.format("%s %2.2f°C (%d/%d)", d.getPlaceFromUrl(), d.getAir(), d.getPos(), d.getCount());
  }

  @Override
  public void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) throws HokanException {
    String[] places = results.getStringArray(ARG_PLACE);
    String regexp = "";
    for (String plc : places) {
      regexp = String.format(".*%s.*|%s", plc, regexp);
    }
    ServiceResponse serviceResponse = doServicesRequest(ServiceRequestType.WEATHER_REQUEST, request.getIrcEvent(), ".*");
    List<KelikameratWeatherData> datas = serviceResponse.getWeatherResponse();
    KelikameratWeatherData winner = null;
    for (KelikameratWeatherData data  : datas) {
      if (data.getPlaceFromUrl().matches(regexp)) {
        if (winner == null) {
          winner = data;
        } else {
          if (data.getAir() > winner.getAir()) {
            winner = data;
          }
        }
      }
    }
    if (winner != null) {
      response.addResponse("%s", formatWeather(winner));
    } else {
      response.addResponse("Canada!!!");
    }
  }

}
