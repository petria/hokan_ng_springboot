package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.UnflaggedOption;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.command.HelpGroup;
import org.freakz.hokan_ng_springboot.bot.command.annotation.HelpGroups;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.freakz.hokan_ng_springboot.bot.util.StaticStrings.ARG_CHANNEL_ID;

/**
 * Created by Petri Airio on 12.10.2015.
 *
 */
@Component
@Scope("prototype")
@Slf4j
@HelpGroups(
    helpGroups = {HelpGroup.CHANNELS, HelpGroup.PROPERTIES}
)
public class ChanViewCmd extends Cmd {

  public ChanViewCmd() {
    super();
    setHelp("Show channel information.");

    UnflaggedOption unflaggedOption = new UnflaggedOption(ARG_CHANNEL_ID)
        .setRequired(false)
        .setGreedy(false);
    registerParameter(unflaggedOption);

  }

  @Override
  public void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) throws HokanException {
    String channelId = results.getString(ARG_CHANNEL_ID, null);
    if (request.getIrcEvent().isPrivate() && channelId == null) {
      response.addResponse("ChannelID parameter is needed when using private message, try: !chanlist to get ID.");
      return;
    }
    Channel theChannel = request.getChannel();
    if (channelId != null) {
      long id;
      try {
        id = Long.parseLong(channelId);
      } catch (NumberFormatException ex) {
        response.addResponse("Valid ChannelID parameter is needed, try: !chanlist");
        return;
      }
      theChannel = channelService.findOne(id);
      if (theChannel == null) {
        response.addResponse("No valid Channel found with id: %d, try: !chanlist to get ID.", id);
        return;
      }
    }

  }
}
