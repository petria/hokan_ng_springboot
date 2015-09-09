package org.freakz.hokan_ng_springboot.bot;

/**
 * Created by Petri Airio on 19.3.2015.
 *
 */

import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.User;
import org.freakz.hokan_ng_springboot.bot.jpa.service.UserService;
import org.freakz.hokan_ng_springboot.bot.service.AccessControlService;

import java.util.List;

@Slf4j
public class HokanAuthenticatedWebSession extends AuthenticatedWebSession {

  @SpringBean
  private UserService userService;

  @SpringBean
  private AccessControlService accessControlService;

  private User loggedInUser;

  public HokanAuthenticatedWebSession(Request request) {
    super(request);
    Injector.get().inject(this);
  }

  @Override
  public boolean authenticate(final String username, final String password) {
    return checkAccess(username, password);
  }

  private boolean checkAccess(final String username, final String password) {
    List<User> userList = userService.findAll();
    for (User user : userList) {
      if (user.getNick().equalsIgnoreCase(username)) {
        log.info("User exists");
        if (user.getPassword().equals(password)) {
          log.info("User authorized: {}", username);
          this.loggedInUser = accessControlService.loginUser(user);
          return true;
        } else {
          log.info("User invalid password: {}", username);
          return false;
        }
      }
    }
    return false;
  }

  @Override
  public Roles getRoles() {
    if (isSignedIn()) {
      Roles resultRoles = new Roles();
      resultRoles.add(Roles.USER);
      if (accessControlService.isAdminUser(this.loggedInUser)) {
        log.info("User {} is ADMIN!", this.loggedInUser);
        resultRoles.add(Roles.ADMIN);
      }
      return resultRoles;
    }
    return null;
  }

  public void logoffUser() {
    accessControlService.logoffUser(this.loggedInUser);
    this.loggedInUser = null;
  }

  public User getUser() {
    return this.loggedInUser;
  }

  @Override
  public void onInvalidate() {
    super.onInvalidate();
    log.info("invalidate: {}", loggedInUser);
  }

}
