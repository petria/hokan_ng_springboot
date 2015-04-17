package org.freakz.hokan_ng_springboot.bot.jpa.service;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.User;

import java.util.List;

/**
 * Created by Petri Airio on 11.3.2015.
 *
 */
public interface UserService {

  List<User> findAll();

  User findFirstByNick(String nick);

  User findById(long id);

  User save(User user);

  User getUserByMask(String mask);


}
