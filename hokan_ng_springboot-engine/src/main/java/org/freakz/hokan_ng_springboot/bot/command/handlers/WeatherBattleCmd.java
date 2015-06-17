package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.UnflaggedOption;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.events.ServiceRequestType;
import org.freakz.hokan_ng_springboot.bot.events.ServiceResponse;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.models.WeatherData;
import org.freakz.hokan_ng_springboot.bot.util.StringStuff;
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

  @Override
  public void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) throws HokanException {
    String[] places = results.getStringArray(ARG_PLACE);
    String regexp = "";
    for (String plc : places) {
      regexp = String.format(".*%s.*|%s", plc, regexp);
    }
    ServiceResponse serviceResponse = doServicesRequest(ServiceRequestType.WEATHER_REQUEST, request.getIrcEvent(), ".*");
    List<WeatherData> datas = serviceResponse.getWeatherResponse();
    WeatherData winner = null;
    for (WeatherData data  : datas) {
      if (data.getCity().matches(regexp)) {
        if (winner == null) {
          winner = data;
        } else {
          if (data.getTemp1() > winner.getTemp1()) {
            winner = data;
          }
        }
      }
    }
    if (winner != null) {
      response.addResponse("%s", StringStuff.fillTemplate(FORMAT, winner.getData()));
    } else {
      response.addResponse("Canada!!!");
    }
  }

}
