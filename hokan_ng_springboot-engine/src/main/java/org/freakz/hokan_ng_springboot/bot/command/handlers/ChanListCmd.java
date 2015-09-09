package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.Switch;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Network;
import org.freakz.hokan_ng_springboot.bot.jpa.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.freakz.hokan_ng_springboot.bot.util.StaticStrings.ARG_VERBOSE;

/**
 * User: petria
 * Date: 12/5/13
 * Time: 9:24 AM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Component
@Scope("prototype")
public class ChanListCmd extends Cmd {

  @Autowired
  private ChannelService channelService;

  public ChanListCmd() {
    super();
    setHelp("Shows what channels the Bot knows of.");
    addToHelpGroup(HelpGroup.CHANNELS, this);

    Switch sw = new Switch(ARG_VERBOSE)
        .setShortFlag('v');
    registerParameter(sw);

  }

  @Override
  public void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) throws HokanException {
    boolean verbose = results.getBoolean(ARG_VERBOSE);
    List<Channel> channels;
    if (verbose) {
      channels = channelService.findAll();
    } else {
      channels = channelService.findByChannelNameLike("#%");
    }

    StringBuilder sb = new StringBuilder();
    for (Channel channel : channels) {
      if (sb.length() > 0) {
        sb.append(", ");
      }
      Network network = channel.getNetwork();
      String ch = String.format("[%d] %s(%s): %s",
          channel.getId(),
          channel.getChannelName(),
          network.getName(),
          channel.getChannelState());
      sb.append(ch);
    }
    response.addResponse("Known channels: ");
    response.addResponse(sb.toString());
  }

}
