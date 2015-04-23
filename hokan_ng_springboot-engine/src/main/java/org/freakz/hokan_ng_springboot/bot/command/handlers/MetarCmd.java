package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.UnflaggedOption;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.freakz.hokan_ng_springboot.bot.util.StaticStrings.ARG_STATION;

/**
 * Created by Petri Airio on 22.4.2015.
 *
 */
@Component
@Scope("prototype")
public class MetarCmd extends Cmd {

  public MetarCmd() {
    super();
    setHelp("Queries Metar weather datas. See: http://en.wikipedia.org/wiki/METAR");

    UnflaggedOption opt = new UnflaggedOption(ARG_STATION)
        .setRequired(true)
        .setGreedy(false);
    registerParameter(opt);

  }

  @Override
  public String getMatchPattern() {
    return "!metar.*";
  }


  @Override
  public void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) throws HokanException {
    doServicesRequest("METAR", request.getIrcEvent());
    //

  }

}
