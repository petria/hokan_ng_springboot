package org.freakz.hokan_ng_springboot.bot;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.User;
import org.freakz.hokan_ng_springboot.bot.jpa.service.UserService;
import org.freakz.hokan_ng_springboot.bot.model.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * A custom authentication provider. Uses the UserService to check authentication.
 *
 * @author aakin
 *
 */
@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

  @Autowired
  private UserService userService;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    String password = (String) authentication.getCredentials();

    List<User> userList = userService.findAll();
    for (User user : userList) {
      if (user.getNick().equalsIgnoreCase(username)) {
        log.info("User exists");
        if (user.getPassword().equals(password)) {
          log.info("User authorized: {}", username);
          Role r = new Role();
          r.setName("ROLE_USER");
          List<Role> roles = new ArrayList<Role>();
          roles.add(r);
          if (user.getFlags().contains("A")) {
            r = new Role();
            r.setName("ROLE_ADMIN");
            roles.add(r);
          }
          return new UsernamePasswordAuthenticationToken(user, password, roles);
        } else {
          log.info("User invalid password: {}", username);
        }
      }
    }
    throw new BadCredentialsException("Access denied.");

  }

  @Override
  public boolean supports(Class<?> arg0) {
    return true;
  }

}
