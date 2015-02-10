package org.freakz.hokan_ng_sprintboot.common.jpa.dao;

import org.freakz.hokan_ng_sprintboot.common.exception.HokanDAOException;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.User;

import java.util.List;

/**
 * User: petria
 * Date: 11/8/13
 * Time: 5:49 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
public interface UserDAO {

  User findUser(String nick) throws HokanDAOException;

  List<User> findUsers() throws HokanDAOException;

  List<User> getLoggedInUsers() throws HokanDAOException;

  void resetLoggedInUsers() throws HokanDAOException;

  void resetOlpos() throws HokanDAOException;

  User updateUser(User user) throws HokanDAOException;

}
