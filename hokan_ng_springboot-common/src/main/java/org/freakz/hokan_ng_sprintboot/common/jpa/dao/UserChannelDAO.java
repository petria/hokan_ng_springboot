package org.freakz.hokan_ng_sprintboot.common.jpa.dao;

import org.freakz.hokan_ng_sprintboot.common.exception.HokanDAOException;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Channel;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.User;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.UserChannel;

import java.util.List;

/**
 * User: petria
 * Date: 11/9/13
 * Time: 4:04 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
public interface UserChannelDAO {

  UserChannel createUserChannel(User user, Channel channel) throws HokanDAOException;

  UserChannel getUserChannel(User user, Channel channel) throws HokanDAOException;

  List<UserChannel> findUserChannels(User user) throws HokanDAOException;

  UserChannel storeUserChannel(UserChannel userChannel);
}
