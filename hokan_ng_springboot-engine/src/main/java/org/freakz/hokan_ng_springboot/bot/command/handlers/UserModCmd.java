package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.Switch;
import com.martiansoftware.jsap.UnflaggedOption;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.events.IrcEvent;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.User;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.UserChannel;
import org.freakz.hokan_ng_springboot.bot.jpa.service.UserChannelService;
import org.freakz.hokan_ng_springboot.bot.jpa.service.UserService;
import org.freakz.hokan_ng_springboot.bot.service.AccessControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.freakz.hokan_ng_springboot.bot.util.StaticStrings.*;

/**
 * User: petria
 * Date: 12/18/13
 * Time: 11:15 AM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Component
@Slf4j
@Scope("prototype")
public class UserModCmd extends Cmd {

  @Autowired
  private AccessControlService accessControlService;
  @Autowired
  private UserChannelService userChannelService;
  @Autowired
  private UserService userService;

  public UserModCmd() {
    super();
    setHelp("Modify user information.");
    addToHelpGroup(HelpGroup.USERS, this);

    Switch sw = new Switch(ARG_VERBOSE)
        .setDefault("false")
        .setShortFlag('v');
    registerParameter(sw);

    FlaggedOption flg = new FlaggedOption(ARG_EMAIL)
        .setRequired(false)
        .setLongFlag("email")
        .setShortFlag('e');
    registerParameter(flg);

    flg = new FlaggedOption(ARG_FLAGS)
        .setRequired(false)
        .setLongFlag("flags")
        .setShortFlag('f');
    registerParameter(flg);

    flg = new FlaggedOption(ARG_FULL_NAME)
        .setRequired(false)
        .setLongFlag("fullname")
        .setShortFlag('n');
    registerParameter(flg);

    flg = new FlaggedOption(ARG_JOIN_MSG)
        .setRequired(false)
        .setLongFlag("joinmsg")
        .setShortFlag('j');
    registerParameter(flg);

    flg = new FlaggedOption(ARG_MASK)
        .setRequired(false)
        .setLongFlag("mask")
        .setShortFlag('m');
    registerParameter(flg);

    flg = new FlaggedOption(ARG_PHONE)
        .setRequired(false)
        .setLongFlag("phone")
        .setShortFlag('p');
    registerParameter(flg);

    UnflaggedOption opt = new UnflaggedOption(ARG_NICK)
        .setRequired(false)
        .setGreedy(false);
    registerParameter(opt);

  }

  @Override
  public void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) throws HokanException {
    String mask = results.getString(ARG_MASK);
    String target = results.getString(ARG_NICK, "me");
    String email = results.getString(ARG_EMAIL);
    String flags = results.getString(ARG_FLAGS);
    String fullName = results.getString(ARG_FULL_NAME);
    String joinMsg = results.getString(ARG_JOIN_MSG);
    String phone = results.getString(ARG_PHONE);

    User hUser;
    IrcEvent iEvent = request.getIrcEvent();
    if (target.equals("me")) {
      hUser = request.getUser();
    } else {
      if (accessControlService.isAdminUser(request.getUser())) {
        hUser = userService.findFirstByNick(target);
      } else {
        response.addResponse("Only MasterUsers can modify others data!");
        return;
      }
    }

    if (hUser == null) {
      response.addResponse("No User found with: " + target);
      return;
    }
    UserChannel userChannel = userChannelService.getUserChannel(hUser, request.getChannel());

    String ret = "";
    boolean updateUserChannel = false;
    if (email != null) {
      String old = hUser.getEmail();
      hUser.setEmail(email);
      ret += "Email    : '" + old + "' -> '" + email + "'\n";
    }
    if (flags != null) {
      String old = hUser.getFlags();
      hUser.setFlags(flags);
      ret += "Flags    : '" + old + "' -> '" + flags + "'\n";
    }
    if (fullName != null) {
      String old = hUser.getFullName();
      hUser.setFullName(fullName);
      ret += "FullName : '" + old + "' -> '" + fullName + "'\n";
    }
    if (joinMsg != null) {
      String old = userChannel.getJoinComment();
      userChannel.setJoinComment(joinMsg);
      ret += "JoinMsg  : '" + old + "' -> '" + joinMsg + "'\n";
      updateUserChannel = true;
    }
    if (mask != null) {
      String old = hUser.getMask();
      hUser.setMask(mask);
      ret += "Mask     : '" + old + "' -> '" + mask + "'\n";
    }
    if (phone != null) {
      String old = hUser.getPhone();
      hUser.setPhone(phone);
      ret += "Phone  : '" + old + "' -> '" + phone + "'\n";
    }

    if (ret.length() > 0) {
      hUser = userService.save(hUser);
      if (updateUserChannel) {
        userChannelService.save(userChannel);
      }
      if (results.getBoolean(ARG_VERBOSE)) {
        response.addResponse(hUser.getNick() + " datas modified: \n" + ret);
      } else {
        response.addResponse("Modified!");
      }
    } else {
      response.addResponse("Nothing modified!");
    }
  }

}
