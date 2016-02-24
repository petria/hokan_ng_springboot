package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAPResult;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;

import static org.freakz.hokan_ng_springboot.bot.util.StaticStrings.ARG_FLAGS;

/**
 * Created by Petri Airio on 24.2.2016.
 * -
 */
public class UserFlagsSetCmd extends Cmd {

  public UserFlagsSetCmd() {
    super();
    setHelp("Modifies user flags.");

    FlaggedOption flaggedOption = new FlaggedOption(ARG_FLAGS)
        .setRequired(false)
        .setLongFlag("flags")
        .setShortFlag('f');
    registerParameter(flaggedOption);
  }

  @Override
  public void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) throws HokanException {

  }

}
