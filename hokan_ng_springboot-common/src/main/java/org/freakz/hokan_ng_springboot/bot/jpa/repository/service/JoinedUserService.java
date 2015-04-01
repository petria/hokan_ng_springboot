package org.freakz.hokan_ng_springboot.bot.jpa.repository.service;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;

/**
 * Created by Petri Airio on 1.4.2015.
 */
public interface JoinedUserService {
  void clearJoinedUsers(Channel channel);
}
