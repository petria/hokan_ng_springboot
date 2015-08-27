package org.freakz.hokan_ng_springboot.bot.jpa.service;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.JoinedUser;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Network;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.User;

import java.util.List;

/**
 * Created by Petri Airio on 1.4.2015.
 *
 */
public interface JoinedUserService {

  void clearJoinedUsers(Channel channel);

  JoinedUser createJoinedUser(Channel channel, User user, String userModes);

  List<JoinedUser> findJoinedUsers(Channel channel);

  List<JoinedUser> findJoinedUsersByNetwork(Network network);

}
