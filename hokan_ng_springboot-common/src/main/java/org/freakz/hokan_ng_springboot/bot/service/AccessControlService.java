package org.freakz.hokan_ng_springboot.bot.service;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.User;

/**
 * Created by Petri Airio on 23.3.2015.
 */
public interface AccessControlService {

  boolean isAdminUser(User user);

  boolean isChannelOp(User user, Channel Channel);


  boolean isLoggedIn(User isLoggedIn);

  User loginUser(User user2);

  User logoffUser(User user2);
}
