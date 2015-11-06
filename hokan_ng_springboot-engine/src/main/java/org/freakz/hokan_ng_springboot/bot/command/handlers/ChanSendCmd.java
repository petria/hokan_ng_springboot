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
import static org.freakz.hokan_ng_springboot.bot.util.StaticStrings.ARG_MESSAGE;

/**
 * Created by Petri Airio on 6.11.2015.
 * -
 */
@Component
@Scope("prototype")
@Slf4j
@HelpGroups(
    helpGroups = {HelpGroup.CHANNELS}
)
public class ChanSendCmd extends Cmd {

  public ChanSendCmd() {
    super();
    setHelp("Sends message to specified channel.");
    setChannelOpOnly(true);

    UnflaggedOption unflaggedOption = new UnflaggedOption(ARG_CHANNEL_ID)
        .setRequired(true)
        .setGreedy(false);
    registerParameter(unflaggedOption);

    unflaggedOption = new UnflaggedOption(ARG_MESSAGE)
        .setRequired(true)
        .setGreedy(false);
    registerParameter(unflaggedOption);

  }

  @Override
  public void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) throws HokanException {
    String channelId = getChannelIdOrFail(results.getString(ARG_CHANNEL_ID, null), request, response);
    if (channelId == null) {
      return;
    }
    Channel theChannel = getChannelOrFail(channelId, request, response);
    if (theChannel == null) {
      return;
    }

  }
}
