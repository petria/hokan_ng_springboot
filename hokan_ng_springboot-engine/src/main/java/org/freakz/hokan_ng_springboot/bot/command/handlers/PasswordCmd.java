package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.UnflaggedOption;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.command.annotation.HelpGroups;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.User;
import org.freakz.hokan_ng_springboot.bot.util.StringStuff;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.freakz.hokan_ng_springboot.bot.util.StaticStrings.*;

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
@HelpGroups(
    helpGroups = {HelpGroup.USERS}
)
public class PasswordCmd extends Cmd {

  public PasswordCmd() {
    super();
    setHelp("Sets user password.");
    setPrivateOnly(true);

    FlaggedOption flaggedOption = new FlaggedOption(ARG_NICK)
        .setRequired(false)
        .setLongFlag("nick")
        .setShortFlag('n');
    registerParameter(flaggedOption);

    UnflaggedOption flg = new UnflaggedOption(ARG_OLD_PASSWORD)
        .setRequired(true)
        .setGreedy(false);
    registerParameter(flg);

    flg = new UnflaggedOption(ARG_NEW_PASSWORD1)
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
    String target = results.getString(ARG_NICK, "me");
    User user;
    if (target.equals("me")) {
      user = request.getUser();
    } else {
      if (accessControlService.isAdminUser(request.getUser())) {
        user = userService.findFirstByNick(target);
      } else {
        response.addResponse("Only Admins can log off other users!");
        return;
      }
    }

    if (user == null) {
      response.addResponse("No User found with: " + target);
      return;
    }

    String oldPassword = results.getString(ARG_OLD_PASSWORD);
    boolean authOk = accessControlService.authenticate(user, oldPassword);
    if (!authOk) {
      if (!accessControlService.isAdminUser(request.getUser())) {
        response.addResponse("Old password incorrect!");
        return;
      }
    }
    String password1 = results.getString(ARG_NEW_PASSWORD1);
    String password2 = results.getString(ARG_NEW_PASSWORD2);
    if (password1.equals(password2)) {
      user.setPassword(StringStuff.getSHA1Password(password1));
      userService.save(user);
      response.addResponse("%s password changed to: %s", user.getNick(), password1);
    } else {
      response.addResponse("New passwords mismatch!");
    }
  }

}
