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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.freakz.hokan_ng_springboot.bot.util.StaticStrings.ARG_FLAGS;
import static org.freakz.hokan_ng_springboot.bot.util.StaticStrings.ARG_NICK;

/**
 * Created by Petri Airio on 24.2.2016.
 * -
 */
@Component
@Slf4j
@Scope("prototype")
@HelpGroups(
    helpGroups = {HelpGroup.ACCESS_CONTROL, HelpGroup.USERS}
)
public class UserFlagsSetCmd extends Cmd {

  public UserFlagsSetCmd() {

    setHelp("Modifies user flags.");

    UnflaggedOption opt = new UnflaggedOption(ARG_NICK)
        .setRequired(true)
        .setGreedy(false);
    registerParameter(opt);

    opt = new UnflaggedOption(ARG_FLAGS)
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
      response.addResponse("No User found with: " + target);
      return;
    }

    String flagsStr = results.getString(ARG_FLAGS, null);
    if (flagsStr == null) {

    }
  }

}
