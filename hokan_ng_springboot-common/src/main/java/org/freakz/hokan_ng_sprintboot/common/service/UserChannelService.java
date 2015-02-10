package org.freakz.hokan_ng_sprintboot.common.service;

import org.freakz.hokan_ng_sprintboot.common.exception.HokanException;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Channel;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.User;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.UserChannel;

import java.util.List;

/**
 * User: petria
 * Date: 11/9/13
 * Time: 4:30 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
public interface UserChannelService {

  UserChannel createUserChannel(User user, Channel channel);

  UserChannel getUserChannel(User user, Channel channel);

  List<UserChannel> findUserChannels(User user) throws HokanException;

  UserChannel storeUserChannel(UserChannel userChannel) throws HokanException;

}
