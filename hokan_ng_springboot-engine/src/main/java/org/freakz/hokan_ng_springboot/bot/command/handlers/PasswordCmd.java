package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.UnflaggedOption;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.util.StringStuff;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.freakz.hokan_ng_springboot.bot.util.StaticStrings.ARG_NEW_PASSWORD1;
import static org.freakz.hokan_ng_springboot.bot.util.StaticStrings.ARG_NEW_PASSWORD2;

/**
 * User: petria
 * Date: 12/19/13
 * Time: 10:07 AM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Component
@Scope("prototype")
@Slf4j
public class PasswordCmd extends Cmd {

  public PasswordCmd() {
    super();
    setHelp("PasswordCmd help");
    addToHelpGroup(HelpGroup.USERS, this);
    setPrivateOnly(true);

    UnflaggedOption flg = new UnflaggedOption(ARG_NEW_PASSWORD1)
        .setRequired(true)
        .setGreedy(false);
    registerParameter(flg);

    flg = new UnflaggedOption(ARG_NEW_PASSWORD2)
        .setRequired(true)
        .setGreedy(false);
    registerParameter(flg);

  }

  @Override
  public void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) throws HokanException {
    String password1 = results.getString(ARG_NEW_PASSWORD1);
    String password2 = results.getString(ARG_NEW_PASSWORD2);
    if (password1.equals(password2)) {
      request.getUser().setPassword(StringStuff.md5(password1));
      request.updateUser();
      response.addResponse("Password changed to: %s", password1);
    } else {
      response.addResponse("New passwords mismatch!");
    }
  }

}
