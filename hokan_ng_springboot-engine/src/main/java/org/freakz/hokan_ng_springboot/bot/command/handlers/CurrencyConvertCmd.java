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

import static org.freakz.hokan_ng_springboot.bot.util.StaticStrings.*;

/**
 * Created by Petri Airio on 2.9.2015.
 *
 */
@Component
@Scope("prototype")
@Slf4j
public class CurrencyConvertCmd extends Cmd {

  public CurrencyConvertCmd() {
    super();
    setHelp("Converts currencies via http://www.google.com/finance/converter");

    UnflaggedOption flg = new UnflaggedOption(ARG_AMOUNT)
        .setRequired(true)
        .setGreedy(false);
    registerParameter(flg);

    flg = new UnflaggedOption(ARG_FROM)
        .setRequired(true)
        .setGreedy(false);
    registerParameter(flg);

    flg = new UnflaggedOption(ARG_TO)
        .setRequired(true)
        .setGreedy(false);
    registerParameter(flg);

  }

  @Override
  public void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) throws HokanException {
    String amount = results.getString(ARG_AMOUNT);
    String from = results.getString(ARG_FROM);
    String to = results.getString(ARG_TO);

    ServiceResponse serviceResponse = doServicesRequest(ServiceRequestType.CURRENCY_CONVERT_REQUEST,
        request.getIrcEvent(), amount, from, to);
    response.addResponse("%s", serviceResponse.getCurrencyConvertResponse());

  }
}
