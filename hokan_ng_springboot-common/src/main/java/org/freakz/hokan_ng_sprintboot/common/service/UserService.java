package org.freakz.hokan_ng_sprintboot.common.service;

import org.freakz.hokan_ng_sprintboot.common.exception.HokanServiceException;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.User;

import java.util.List;

/**
 * User: petria
 * Date: 11/9/13
 * Time: 3:42 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
public interface UserService {

  User findUser(String nick) throws HokanServiceException;

  List<User> findUsers() throws HokanServiceException;

  User getUserByMask(String mask) throws HokanServiceException;

  List<User> getLoggedInUsers() throws HokanServiceException;

  void resetLoggedInUsers() throws HokanServiceException;

  void resetOlpos() throws HokanServiceException;

  User updateUser(User user) throws HokanServiceException;

}
