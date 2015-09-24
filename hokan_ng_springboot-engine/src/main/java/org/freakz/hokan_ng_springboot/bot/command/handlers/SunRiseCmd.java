package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.UnflaggedOption;
import org.freakz.hokan_ng_springboot.bot.command.HelpGroup;
import org.freakz.hokan_ng_springboot.bot.command.annotation.HelpGroups;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.freakz.hokan_ng_springboot.bot.util.StaticStrings.ARG_CITY;

/**
 * Created by Petri Airio on 24.9.2015.
 *
 */
@Component
@Scope("prototype")
@HelpGroups(
    helpGroups = {HelpGroup.DATA_FETCHERS}
)
public class SunRiseCmd extends Cmd {

  public SunRiseCmd() {
    super();
    setHelp("Queries Sun rise / set times from Ilmatieteenlaitos web pages.");

    UnflaggedOption unflaggedOption = new UnflaggedOption(ARG_CITY)
        .setRequired(true)
        .setGreedy(false);
    registerParameter(unflaggedOption);

  }

  @Override
  public void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) throws HokanException {
    String city = results.getString(ARG_CITY);
    String baseUrl = "http://en.ilmatieteenlaitos.fi/weather/";
    String url = baseUrl + city;
    boolean error = false;
    String ret = "";
    Document doc;
    try {
      doc = Jsoup.connect(url).get();
      Elements value = doc.getElementsByAttributeValue("class", "local-weather-main-title");
      String place = value.get(0).text();
      Elements value2 = doc.getElementsByAttributeValue("class", "celestial-text");
      if (value2.size() == 0) {
        error = true;
      } else {
        String sunrise = value2.get(1).text();
        ret += String.format("%s: %s", place.split(" ")[0], sunrise);
      }
    } catch (IOException e) {
      error = true;
    }
    if (error) {
      response.addResponse("No Sun rise/set data found with: %s", city);
    } else {
      response.addResponse(ret);
    }
  }

}
