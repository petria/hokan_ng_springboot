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
import org.freakz.hokan_ng_springboot.bot.jpa.entity.UserChannel;
import org.freakz.hokan_ng_springboot.bot.jpa.service.UserChannelService;
import org.freakz.hokan_ng_springboot.bot.jpa.service.UserService;
import org.freakz.hokan_ng_springboot.bot.util.StringStuff;
import org.freakz.hokan_ng_springboot.bot.util.Uptime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

import static org.freakz.hokan_ng_springboot.bot.util.StaticStrings.ARG_NICK;


/**
 *
 * Created by bzr on 10.11.2014.
 */
@Component
@Slf4j
@Scope("prototype")
@HelpGroups(
    helpGroups = {HelpGroup.USERS}
)
public class LastSeenCmd extends Cmd {

  @Autowired
  private UserService userService;

  @Autowired
  private UserChannelService userChannelService;

  public LastSeenCmd() {
    super();
    setHelp("Shows when user has last seen.");

    UnflaggedOption flg = new UnflaggedOption(ARG_NICK)
        .setRequired(false)
        .setGreedy(false);
    registerParameter(flg);
  }

  @Override
  public void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) throws HokanException {
    String nick = results.getString(ARG_NICK);
    String botNick = request.getIrcEvent().getBotNick();
    User hUser;

    if (nick == null) {
      hUser = request.getUser();
    } else {
      hUser = userService.findFirstByNick(nick);
      if (nick.equals(botNick)) {
        response.addResponse("I'm here DUMBASS!");
        return;
      }
    }
    if (hUser == null) {
      response.addResponse("PircBotUser not found: %s", nick);
      return;
    }

    List<UserChannel> userChannels = userChannelService.findByUser(hUser);
    if (userChannels.size() > 0) {
      UserChannel channel = userChannels.get(0);
      String ret = hUser.getNick();
      ret += channel.getChannel().getChannelName();
      String[] format = {"00", "00", "00", "0"};
      Date lastSpoke = channel.getLastMessageTime();
      if (lastSpoke != null) {
        ret += " was last time seen on ";
        long temp = lastSpoke.getTime();
        Uptime uptime = new Uptime(temp);
        Integer[] ut = uptime.getTimeDiff();
        String ret2 = StringStuff.fillTemplate(" and last spoke %3 days and %2:%1:%0 ago", ut, format);
        ret += ret2;
      } else {
        ret += " has not spoken yet!";
      }
      response.addResponse(ret);
    } else {
      response.addResponse("%s not seen on any known channel!", nick);
    }
  }
}