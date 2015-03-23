package org.freakz.hokan_ng_springboot.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.User;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Petri Airio on 23.3.2015.
 *
 */
@Service
@Slf4j
public class AccessControlServiceImpl implements AccessControlService {

  @Autowired
  private UserService userService;

  @Override
  public boolean isAdminUser(User isAdmin) {
    User user = userService.findById(isAdmin.getUserId());
    if (user == null) {
      log.debug("User not found: {}", user);
      return false;
    }
    String flags = user.getFlags();
    if (flags == null) {
      log.debug("User {} flags null!", user);
      return false;
    }
    return flags.contains("A");
  }

  @Override
  public boolean isChannelOp(User user, Channel Channel) {
    return true;
  }

  @Override
  public boolean isLoggedIn(User isLoggedIn) {
    User user = userService.findById(isLoggedIn.getUserId());
    if (user == null) {
      log.debug("User not found: {}", user);
      return false;
    }
    return user.isLoggedIn() > 0;
  }

  @Override
  public User loginUser(User user2) {
    User user = userService.findById(user2.getUserId());
    if (user == null) {
      log.debug("User not found: {}", user);
      return null;
    }
    user.setLoggedIn(1);
    log.info("User logged in: {}", user);
    return userService.save(user);
  }

  @Override
  public User logoffUser(User user2) {
    User user = userService.findById(user2.getUserId());
    if (user == null) {
      log.debug("User not found: {}", user);
      return null;
    }
    user.setLoggedIn(0);
    log.info("User logged off: {}", user);
    return userService.save(user);
  }

}
