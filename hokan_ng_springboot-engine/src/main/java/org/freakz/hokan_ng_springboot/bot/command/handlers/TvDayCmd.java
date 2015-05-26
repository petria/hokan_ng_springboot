package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.JSAPResult;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.events.ServiceRequestType;
import org.freakz.hokan_ng_springboot.bot.events.ServiceResponse;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.PropertyName;
import org.freakz.hokan_ng_springboot.bot.jpa.service.ChannelPropertyService;
import org.freakz.hokan_ng_springboot.bot.models.TelkkuProgram;
import org.freakz.hokan_ng_springboot.bot.util.StringStuff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * User: petria
 * Date: 12/13/13
 * Time: 8:43 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Component
@Scope("prototype")
@Slf4j
public class TvDayCmd extends Cmd {

  @Autowired
  private ChannelPropertyService properties;

  public TvDayCmd() {
    super();
    setHelp("Is there something interesting (=notified) programs coming from TV?");
    addToHelpGroup(HelpGroup.TV, this);
  }

  @Override
  public void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) throws HokanException {
    boolean notifyInUse = properties.getChannelPropertyAsBoolean(request.getChannel(), PropertyName.PROP_CHANNEL_DO_TVNOTIFY, false);
/*    if (!notifyInUse) {
      response.addResponse("TvNotify not in use in this channel! See: !chanenv / !chanset");
      return;
    }*/
    ServiceResponse serviceResponse = doServicesRequest(ServiceRequestType.TV_DAY_REQUEST, request.getIrcEvent(), request.getChannel(), new Date());
    List<TelkkuProgram> daily = serviceResponse.getTvDayData();
    if (daily.size() == 0) {
      response.addResponse("Nothing interesting in TV today!");
      return;
    }
    String lastChannelStr = "";
    int i = 0;
    String reply = "Programs on TV notify today: ";
    for (TelkkuProgram prg : daily) {
      String channelStr = "[" + prg.getChannel() + "] ";
      if (channelStr.equalsIgnoreCase(lastChannelStr)) {
        lastChannelStr = channelStr;
        channelStr = "";
      } else {
        lastChannelStr = channelStr;
      }
      reply += String.format("%d) %s%s: %s(%d) ", i + 1, channelStr, StringStuff.formatTime(prg.getStartTimeD(), StringStuff.STRING_STUFF_DF_HHMM), prg.getProgram(), prg.getId());
      i++;
    }
    response.addResponse(reply);
  }

}
