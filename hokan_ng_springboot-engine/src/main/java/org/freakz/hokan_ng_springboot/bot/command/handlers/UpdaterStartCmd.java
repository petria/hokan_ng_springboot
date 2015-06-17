package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.UnflaggedOption;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.freakz.hokan_ng_springboot.bot.util.StaticStrings.ARG_UPDATER;

/**
 * Created by Petri Airio on 17.6.2015.
 *
 */
@Component
@Scope("prototype")
@Slf4j
public class UpdaterStartCmd extends Cmd {

  public UpdaterStartCmd() {
    super();
    setHelp("Starts specific updater.");

    UnflaggedOption opt = new UnflaggedOption(ARG_UPDATER)
        .setRequired(true)
        .setGreedy(false);
    registerParameter(opt);

    addToHelpGroup(HelpGroup.UPDATERS, this);
    setAdminUserOnly(true);

  }

  @Override
  public void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) throws HokanException {

  }
}
