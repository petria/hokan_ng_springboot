package org.freakz.hokan_ng_springboot.bot.jpa.service;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.IrcLog;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.User;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.UserChannel;

import java.util.List;

/**
 * Created by Petri Airio on 7.4.2015.
 * -
 */
public interface UserChannelService {

  UserChannel createUserChannel(User user, Channel channel, IrcLog ircLog);

  UserChannel getUserChannel(User user, Channel channel);

  List<UserChannel> findByUser(User user);

  UserChannel save(UserChannel userChannel);

}
