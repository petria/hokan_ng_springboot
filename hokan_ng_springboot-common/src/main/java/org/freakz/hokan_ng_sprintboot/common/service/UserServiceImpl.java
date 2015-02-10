package org.freakz.hokan_ng_sprintboot.common.service;


import org.freakz.hokan_ng_sprintboot.common.exception.HokanDAOException;
import org.freakz.hokan_ng_sprintboot.common.exception.HokanException;
import org.freakz.hokan_ng_sprintboot.common.exception.HokanServiceException;
import org.freakz.hokan_ng_sprintboot.common.jpa.dao.UserDAO;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.User;
import org.freakz.hokan_ng_sprintboot.common.util.StringStuff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: petria
 * Date: 11/9/13
 * Time: 3:42 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserDAO userDAO;

  public UserServiceImpl() {
  }

  @Override
  public User findUser(String nick) {
    try {
      return userDAO.findUser(nick);
    } catch (HokanException e) {
      //
    }
    return null;
  }

  @Override
  public List<User> findUsers() throws HokanServiceException {
    try {
      return userDAO.findUsers();
    } catch (HokanDAOException e) {
      throw new HokanServiceException(e);
    }
  }

  @Override
  public User getUserByMask(String mask) throws HokanServiceException {

    List<User> users = findUsers();
    for (User user : users) {
      if (StringStuff.match(mask, user.getMask())) {
        return user;
      }
    }
    return null;
  }

  @Override
  public List<User> getLoggedInUsers() throws HokanServiceException {
    try {
      return userDAO.getLoggedInUsers();
    } catch (HokanDAOException e) {
      throw new HokanServiceException(e);
    }
  }

  @Override
  public void resetLoggedInUsers() throws HokanServiceException {
    try {
      userDAO.resetLoggedInUsers();
    } catch (HokanDAOException e) {
      throw new HokanServiceException(e);
    }
  }

  @Override
  public void resetOlpos() throws HokanServiceException {
    try {
      userDAO.resetOlpos();
    } catch (HokanDAOException e) {
      throw new HokanServiceException(e);
    }
  }

  @Override
  public User updateUser(User user) throws HokanServiceException {
    try {
      return userDAO.updateUser(user);
    } catch (HokanDAOException e) {
      throw new HokanServiceException(e);
    }
  }

}
