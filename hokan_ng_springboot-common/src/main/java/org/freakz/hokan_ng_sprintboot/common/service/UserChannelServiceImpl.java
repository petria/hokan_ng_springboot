package org.freakz.hokan_ng_sprintboot.common.service;

import org.freakz.hokan_ng_sprintboot.common.exception.HokanDAOException;
import org.freakz.hokan_ng_sprintboot.common.exception.HokanException;
import org.freakz.hokan_ng_sprintboot.common.jpa.dao.UserChannelDAO;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Channel;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.User;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.UserChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: petria
 * Date: 11/9/13
 * Time: 4:30 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Service
public class UserChannelServiceImpl implements UserChannelService {

  @Autowired
  private UserChannelDAO userChannelDAO;

  public UserChannelServiceImpl() {
  }

  @Override
  public UserChannel createUserChannel(User user, Channel channel) {
    try {
      return userChannelDAO.createUserChannel(user, channel);
    } catch (HokanDAOException e) {
      //
    }
    return null;
  }

  @Override
  public UserChannel getUserChannel(User user, Channel channel) {
    try {
      return userChannelDAO.getUserChannel(user, channel);
    } catch (HokanException e) {
      //
    }
    return null;
  }

  @Override
  public List<UserChannel> findUserChannels(User user) throws HokanException {
    return userChannelDAO.findUserChannels(user);
  }

  @Override
  public UserChannel storeUserChannel(UserChannel userChannel) throws HokanException {
    return userChannelDAO.storeUserChannel(userChannel);
  }
}
