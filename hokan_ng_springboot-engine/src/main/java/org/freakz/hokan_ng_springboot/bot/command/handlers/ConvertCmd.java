package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.UnflaggedOption;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.util.StaticStrings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.freakz.hokan_ng_springboot.bot.util.StaticStrings.*;

/**
 * User: petria
 * Date: 12/12/13
 * Time: 10:17 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Component
@Scope("prototype")
@Slf4j
public class ConvertCmd extends Cmd {

  @Autowired
  ApplicationContext context;

  public ConvertCmd() {
    super();
    setHelp("ConvertCmd help");

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

    String changed = doConvert(amount, from, to);

    if (!changed.contains("=")) {
      changed = "Epic failure: check parameters!";
    }
    response.addResponse(changed);
  }

  public String doConvert(String amount, String from, String to) {
    try {
      String url = "http://www.google.com/finance/converter?a=" + amount + "&from=" + from + "&to=" + to;
      Document doc = Jsoup.connect(url).userAgent(StaticStrings.HTTP_USER_AGENT).get();
      Elements value = doc.getElementsByAttributeValue("id", "currency_converter_result");
      return value.get(0).text();
    } catch (Exception e) {
      return e.getMessage();
    }
  }

}
