package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.UnflaggedOption;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.command.HelpGroup;
import org.freakz.hokan_ng_springboot.bot.command.annotation.HelpGroups;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.events.ServiceRequestType;
import org.freakz.hokan_ng_springboot.bot.events.ServiceResponse;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.models.ScriptResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.freakz.hokan_ng_springboot.bot.util.StaticStrings.ARG_SCRIPT;

/**
 * Created by Petri Airio on 5.4.2016.
 * -
 */
@Component
@HelpGroups(
    helpGroups = {HelpGroup.SYSTEM}
)
@Scope("prototype")
@Slf4j
public class ScriptCmd extends Cmd {

  public ScriptCmd() {
    setHelp("Executes JavaScript.");

    UnflaggedOption opt = new UnflaggedOption(ARG_SCRIPT)
        .setRequired(true)
        .setGreedy(false);
    registerParameter(opt);

//    setAdminUserOnly(true);
  }

  @Override
  public void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) throws HokanException {
    String script = results.getString(ARG_SCRIPT);
    ServiceResponse serviceResponse = doServicesRequest(ServiceRequestType.SCRIPT_SERVICE_REQUEST, request.getIrcEvent(), script, "JavaScript");
    ScriptResult scriptResult = serviceResponse.getScriptResult();
    response.addResponse("%s", scriptResult.getScriptOutput());
  }
}
