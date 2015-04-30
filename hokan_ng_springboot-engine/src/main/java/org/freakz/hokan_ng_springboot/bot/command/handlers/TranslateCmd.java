package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.UnflaggedOption;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.events.ServiceRequestType;
import org.freakz.hokan_ng_springboot.bot.events.ServiceResponse;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.freakz.hokan_ng_springboot.bot.util.StaticStrings.ARG_WORD;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 29.4.2015.
 *
 */
@Component
@Scope("prototype")
@Slf4j
public class TranslateCmd extends Cmd {


  public TranslateCmd() {
    super();
    setHelp("FIN-ENG-FIN dictionary..");

    UnflaggedOption flg = new UnflaggedOption(ARG_WORD)
        .setRequired(true)
        .setGreedy(true);
    registerParameter(flg);
  }

  @Override
  public String getMatchPattern() {
    return "!trans.*";
  }

  @Override
  public void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) throws HokanException {
    String[] words = results.getStringArray(ARG_WORD);
    ServiceResponse serviceResponse = doServicesRequest(ServiceRequestType.TRANSLATE_REQUEST, request.getIrcEvent(), words);
    response.addResponse("%s", serviceResponse.getResponseData("TRANSLATE_RESPONSE"));
  }
}
