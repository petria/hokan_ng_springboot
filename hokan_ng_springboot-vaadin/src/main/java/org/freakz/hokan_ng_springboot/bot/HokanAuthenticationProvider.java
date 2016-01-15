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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 *
 *
 */
@Component
@Slf4j
public class HokanAuthenticationProvider implements AuthenticationProvider, UserDetailsService {

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

  @Override
  public UserDetails loadUserByUsername(String nick) throws UsernameNotFoundException {
    User user = userService.findFirstByNick(nick);
    if (user == null) {
      throw new UsernameNotFoundException("Unknown user: " + nick);
    }
    UserDetails userDetails = new UserDetailsWrapper(user);

    return userDetails;
  }

  class UserDetailsWrapper implements UserDetails {

    final private User user;
    public UserDetailsWrapper(User user) {
      this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      Role r = new Role();
      r.setName("ROLE_USER");
      List<Role> roles = new ArrayList<Role>();
      roles.add(r);
      if (user.getFlags().contains("A")) {
        r = new Role();
        r.setName("ROLE_ADMIN");
        roles.add(r);
      }
      return roles;
    }

    @Override
    public String getPassword() {
      return user.getPassword();
    }

    @Override
    public String getUsername() {
      return user.getNick();
    }

    @Override
    public boolean isAccountNonExpired() {
      return false;
    }

    @Override
    public boolean isAccountNonLocked() {
      return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
      return false;
    }

    @Override
    public boolean isEnabled() {
      return true;
    }
  }

}
