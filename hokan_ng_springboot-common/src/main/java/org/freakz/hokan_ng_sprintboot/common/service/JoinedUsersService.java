package org.freakz.hokan_ng_sprintboot.common.service;

import org.freakz.hokan_ng_sprintboot.common.exception.HokanServiceException;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Channel;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.JoinedUser;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Network;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.User;

import java.util.List;

/**
 * User: petria
 * Date: 11/15/13
 * Time: 12:03 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
public interface JoinedUsersService {

  JoinedUser createJoinedUser(Channel channel, User user, String userModes) throws HokanServiceException;

  JoinedUser getJoinedUser(Channel channel, User user);

  JoinedUser getJoinedUser(Channel channel, String nick);

  void removeJoinedUser(Channel channel, User user) throws HokanServiceException;

  List<JoinedUser> findJoinedUsers(Channel channel);

  List<JoinedUser> findJoinedUsers(Network network) throws HokanServiceException;

  void clearJoinedUsers(Channel channel) throws HokanServiceException;

  void clearJoinedUsers() throws HokanServiceException;

}
