package org.freakz.hokan_ng_springboot.bot.jpa.repository.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.User;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Petri Airio on 11.3.2015.
 *
 */
@Service
@Slf4j
public class UserRepositoryService implements UserService {

  @Resource
  private UserRepository userRepository;

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

/*  @Override
  public User findByNick(String nick) {
    return userRepository.findByNick(nick);
  }

  @Override
  public User findById(long id) {
    return userRepository.getOne(id);
  }

  @Override
  public User storeUser(User user) {
    return userRepository.save(user);
  }
*/
}
