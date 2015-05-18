package org.freakz.hokan_ng_springboot.bot.jpa.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.JoinedUser;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.User;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.JoinedUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Petri Airio on 1.4.2015.
 *
 */
@Service
@Slf4j
public class JoinedUserRepositoryService implements JoinedUserService {

  @Autowired
  private JoinedUserRepository repository;

  @Override
  @Transactional
  public void clearJoinedUsers(Channel channel) {
    List<JoinedUser> joinedUsers = repository.findAll();
    log.debug("Deleting {} joined users!", joinedUsers.size());
    repository.delete(joinedUsers);

//    repository.deleteByChannel(channel);
  }

  @Override
  @Transactional
  public JoinedUser createJoinedUser(Channel channel, User user, String userModes) {
    JoinedUser joinedUser = new JoinedUser(channel, user, userModes);
    return repository.save(joinedUser);
  }

  @Override
  public List<JoinedUser> findJoinedUsers(Channel channel) {
    return repository.findByChannel(channel);
  }
}
