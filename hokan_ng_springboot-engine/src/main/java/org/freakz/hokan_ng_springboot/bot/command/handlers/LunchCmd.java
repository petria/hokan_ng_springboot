package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.UnflaggedOption;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.command.HelpGroup;
import org.freakz.hokan_ng_springboot.bot.command.annotation.HelpGroups;
import org.freakz.hokan_ng_springboot.bot.enums.LunchDay;
import org.freakz.hokan_ng_springboot.bot.enums.LunchPlace;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.events.ServiceRequestType;
import org.freakz.hokan_ng_springboot.bot.events.ServiceResponse;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.models.LunchData;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.freakz.hokan_ng_springboot.bot.util.StaticStrings.ARG_LUNCH_PLACE;

/**
 * Created by Petri Airio on 27.1.2016.
 * -
 */
@Component
@Scope("prototype")
@Slf4j
@HelpGroups(
    helpGroups = {HelpGroup.DATA_FETCHERS, HelpGroup.LUNCH}
)
public class LunchCmd extends Cmd {


  public LunchCmd() {
    super();
    setHelp("Shows lunch menu of selected place.");

    UnflaggedOption unflaggedOption = new UnflaggedOption(ARG_LUNCH_PLACE)
        .setRequired(true)
        .setGreedy(false);
    registerParameter(unflaggedOption);

  }

  @Override
  public void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) throws HokanException {
    String argLunchPlace = results.getString(ARG_LUNCH_PLACE);
    LunchPlace place = LunchPlace.getLunchPlace(argLunchPlace);
    if (place == null) {
      response.addResponse("Unknown lunch place: %s\nUse !lunchplaces to see supported places!", argLunchPlace);
      return;
    }
    DateTime day = DateTime.now();
    ServiceResponse serviceResponse = doServicesRequest(ServiceRequestType.LUNCH_REQUEST, request.getIrcEvent(), place, day);
    LunchData lunchData = serviceResponse.getLunchResponse();
    LunchDay lunchDay = LunchDay.getFromDateTime(day);
    response.addResponse("Lunch: %s -> %s", lunchData.getLunchPlace().getName(), lunchData.getMenu().get(lunchDay).getMenu());
  }

}
