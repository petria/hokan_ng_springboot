package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.UnflaggedOption;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.command.HelpGroup;
import org.freakz.hokan_ng_springboot.bot.command.annotation.HelpGroups;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.User;
import org.freakz.hokan_ng_springboot.bot.util.StringStuff;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.freakz.hokan_ng_springboot.bot.util.StaticStrings.ARG_NICK;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 10.9.2015.
 *
 */
@Component
@Scope("prototype")
@Slf4j
@HelpGroups(
    helpGroups = {HelpGroup.ACCESS_CONTROL}
)
public class ResetPasswordCmd extends Cmd {

  public ResetPasswordCmd() {
    super();
    setHelp("Reset user password to random string.");
    setAdminUserOnly(true);

    UnflaggedOption opt = new UnflaggedOption(ARG_NICK)
        .setRequired(false)
        .setGreedy(false);
    registerParameter(opt);

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
        response.addResponse("Only Admins can modify others data!");
        return;
      }
    }

    if (user == null) {
      response.addResponse("No PircBotUser found with: " + target);
      return;
    }

    String generated = StringStuff.generatePasswd(10);
    user.setPassword(StringStuff.getSHA1Password(generated));
    userService.save(user);
    response.addResponse("%s password reset to: %s", user.getNick(), generated);
  }

}
