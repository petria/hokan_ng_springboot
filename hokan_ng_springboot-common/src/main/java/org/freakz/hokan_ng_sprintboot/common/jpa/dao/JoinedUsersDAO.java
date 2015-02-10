package org.freakz.hokan_ng_sprintboot.common.jpa.dao;


import org.freakz.hokan_ng_sprintboot.common.exception.HokanDAOException;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Channel;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.JoinedUser;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Network;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.User;

import java.util.List;

/**
 * User: petria
 * Date: 11/13/13
 * Time: 2:43 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
public interface JoinedUsersDAO {

  JoinedUser createJoinedUser(Channel channel, User user, String userModes) throws HokanDAOException;

  JoinedUser getJoinedUser(Channel channel, User user) throws HokanDAOException;

  JoinedUser getJoinedUser(Channel channel, String nick) throws HokanDAOException;

  void removeJoinedUser(Channel channel, User user) throws HokanDAOException;

  void removeJoinedUser(User user) throws HokanDAOException;

  List<JoinedUser> findJoinedUsers(Channel channel) throws HokanDAOException;

  List<JoinedUser> findJoinedUsers(Network network) throws HokanDAOException;

  void clearJoinedUsers(Channel channel) throws HokanDAOException;

  void clearJoinedUsers() throws HokanDAOException;

}
