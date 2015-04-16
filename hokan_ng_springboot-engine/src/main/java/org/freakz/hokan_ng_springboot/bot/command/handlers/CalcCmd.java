package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.UnflaggedOption;
import org.cheffo.jeplite.JEP;
import org.cheffo.jeplite.ParseException;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.freakz.hokan_ng_springboot.bot.util.StaticStrings.ARG_EXPRESSION;

/**
 * User: petria
 * Date: 11/27/13
 * Time: 3:20 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Component
@Scope("prototype")
public class CalcCmd extends Cmd {

  private final JEP jep;

  public CalcCmd() {
    super();
    setHelp("I am your pocket calculator.");

    jep = new JEP();
    jep.addStandardConstants();
    jep.addStandardFunctions();

    UnflaggedOption flg = new UnflaggedOption(ARG_EXPRESSION)
        .setRequired(true)
        .setGreedy(false);
    registerParameter(flg);

  }

  @Override
  public void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) throws HokanException {
    String result;
    String expression = results.getString(ARG_EXPRESSION);

    jep.parseExpression(expression);

    String error = jep.getErrorInfo();
    if (error != null) {
      result = "Expression parse error: " + error.replaceAll("\"", "");
    } else {
      try {
        result = expression + " = " + jep.getValue();
      } catch (ParseException e) {
        throw new HokanException(e);
      }
    }
    response.addResponse(result);
  }
}
