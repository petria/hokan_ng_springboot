package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.UnflaggedOption;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.freakz.hokan_ng_springboot.bot.util.StaticStrings.ARG_CHANNEL;
import static org.freakz.hokan_ng_springboot.bot.util.StaticStrings.ARG_PASSWORD;

/**
 * User: petria
 * Date: 11/7/13
 * Time: 3:18 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Component
@Scope("prototype")
public class JoinCmd extends Cmd {

  public JoinCmd() {
    super();
    addToHelpGroup(HelpGroup.CHANNELS, this);

    UnflaggedOption uflg = new UnflaggedOption(ARG_CHANNEL)
        .setRequired(true)
        .setGreedy(false);
    registerParameter(uflg);

    uflg = new UnflaggedOption(ARG_PASSWORD)
        .setRequired(false)
        .setGreedy(false);
    registerParameter(uflg);

    setAdminUserOnly(true);

  }

  @Override
  public String getMatchPattern() {
    return "!join.*";
  }

  @Override
  public void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) {
    String channel = results.getString(ARG_CHANNEL);
    String password = results.getString(ARG_PASSWORD);

    response.setResponseMessage("Trying to join to: " + channel);
    if (password != null) {
      response.addEngineMethodCall("joinChannel", channel, password);
    } else {
      response.addEngineMethodCall("joinChannel", channel);
    }
  }
}