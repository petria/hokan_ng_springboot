package org.freakz.hokan_ng_springboot.bot.jpa.repository.service;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.JoinedUser;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.User;

/**
 * Created by Petri Airio on 1.4.2015.
 *
 */
public interface JoinedUserService {

  void clearJoinedUsers(Channel channel);

  JoinedUser createJoinedUser(Channel channel, User user, String userModes);

}
