package org.freakz.hokan_ng_springboot.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.User;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.UserFlag;
import org.freakz.hokan_ng_springboot.bot.jpa.service.UserService;
import org.freakz.hokan_ng_springboot.bot.util.StringStuff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

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
    User user = userService.findById(isAdmin.getId());
    if (user == null) {
      log.debug("User not found: {}", isAdmin);
      return false;
    }
    return user.getUserFlagsSet().contains(UserFlag.ADMIN);
  }

  @Override
  public boolean isChannelOp(User isChannelOp, Channel Channel) {
    User user = userService.findById(isChannelOp.getId());
    if (user == null) {
      log.debug("User not found: {}", isChannelOp);
      return false;
    }
    String flags = user.getFlags();
    if (flags == null) {
      log.debug("User {} flags null!", user);
      return false;
    }
    return flags.contains("C");
  }

  @Override
  public boolean authenticate(User user, String password) {
    String sha1Password = StringStuff.getSHA1Password(password);
    if (user.getPassword().equals(sha1Password)) {
      user.setLoggedIn(1);
      userService.save(user);
      return true;
    }
    return false;
  }

  @Override
  public boolean isLoggedIn(User isLoggedIn) {
    User user = userService.findById(isLoggedIn.getId());
    if (user == null) {
      log.debug("User not found: {}", isLoggedIn);
      return false;
    }
    return user.isLoggedIn() > 0;
  }

  @Override
  public User loginUser(User user2) {
    User user = userService.findById(user2.getId());
    if (user == null) {
      log.debug("User not found: {}", user2);
      return null;
    }
    user.setLoggedIn(1);
    log.info("User logged in: {}", user);
    return userService.save(user);
  }

  @Override
  public User logoffUser(User user2) {
    User user = userService.findById(user2.getId());
    if (user == null) {
      log.debug("User not found: {}", user2);
      return null;
    }
    user.setLoggedIn(0);
    log.info("User logged off: {}", user);
    return userService.save(user);
  }

  @Override
  public boolean hasUserFlag(User user2, UserFlag flag) {
    User user = userService.findById(user2.getId());
    if (user == null) {
      log.debug("User not found: {}", user2);
      return false;
    }
    return user.getUserFlagsSet().contains(flag);
  }

  @Override
  public void addUserFlags(User user2, Set<UserFlag> flagSet) {
    User user = userService.findById(user2.getId());
    if (user == null) {
      log.debug("User not found: {}", user2);
      return;
    }
    user.setFlags("");
  }

  @Override
  public void removeUserFlags(User user, Set<UserFlag> flagSet) {

  }
}
