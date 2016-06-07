package org.freakz.hokan_ng_springboot.bot.jpa.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.IrcLog;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.User;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.UserChannel;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.UserChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Petri Airio on 7.4.2015.
 * -
 */
@Service
@Slf4j
public class UserChannelRepositoryService implements UserChannelService {

  @Autowired
  private UserChannelRepository repository;

  @Override
  @Transactional
  public UserChannel createUserChannel(User user, Channel channel, IrcLog irclog) {
    UserChannel userChannel = new UserChannel(user, channel);
    userChannel.setLastIrcLogID(irclog.getId() + "");
    return repository.save(userChannel);
  }

  @Override
  @Transactional(readOnly = true)
  public UserChannel getUserChannel(User user, Channel channel) {
    return repository.findFirstByUserAndChannel(user, channel);
  }

  @Override
  @Transactional(readOnly = true)
  public List<UserChannel> findByUser(User user) {
    return repository.findByUser(user);
  }

  @Override
  @Transactional
  public UserChannel save(UserChannel userChannel) {
    return repository.save(userChannel);
  }
}
