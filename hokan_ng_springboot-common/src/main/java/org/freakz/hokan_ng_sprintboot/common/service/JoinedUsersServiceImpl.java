package org.freakz.hokan_ng_sprintboot.common.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_sprintboot.common.exception.HokanDAOException;
import org.freakz.hokan_ng_sprintboot.common.exception.HokanServiceException;
import org.freakz.hokan_ng_sprintboot.common.jpa.dao.JoinedUsersDAO;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Channel;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.JoinedUser;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Network;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * User: petria
 * Date: 11/15/13
 * Time: 12:04 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Slf4j
@Service
public class JoinedUsersServiceImpl implements JoinedUsersService {

  @Autowired
  private JoinedUsersDAO dao;

  public JoinedUsersServiceImpl() {
  }

  @Override
  public JoinedUser createJoinedUser(Channel channel, User user, String userModes) throws HokanServiceException {
    try {
      return dao.createJoinedUser(channel, user, userModes);
    } catch (HokanDAOException e) {
      throw new HokanServiceException(e);
    }
  }

  @Override
  public JoinedUser getJoinedUser(Channel channel, User user) {
    try {
      return dao.getJoinedUser(channel, user);
    } catch (HokanDAOException e) {
      log.error("JoinedUser error", e);
    }
    return null;
  }

  @Override
  public JoinedUser getJoinedUser(Channel channel, String nick) {
    try {
      return dao.getJoinedUser(channel, nick);
    } catch (HokanDAOException e) {
      log.error("JoinedUser error", e);
    }
    return null;
  }

  @Override
  public void removeJoinedUser(Channel channel, User user) throws HokanServiceException {
    try {
      dao.removeJoinedUser(channel, user);
    } catch (HokanDAOException e) {
      throw new HokanServiceException(e);
    }
  }

  @Override
  public List<JoinedUser> findJoinedUsers(Channel channel) {
    try {
      return dao.findJoinedUsers(channel);
    } catch (HokanDAOException e) {
      log.info("No joined users for channel {}", channel);
      return new ArrayList<>();
    }
  }

  @Override
  public List<JoinedUser> findJoinedUsers(Network network) throws HokanServiceException {
    try {
      return dao.findJoinedUsers(network);
    } catch (HokanDAOException e) {
      throw new HokanServiceException(e);
    }
  }

  @Override
  public void clearJoinedUsers(Channel channel) throws HokanServiceException {
    try {
      dao.clearJoinedUsers(channel);
    } catch (HokanDAOException e) {
      throw new HokanServiceException(e);
    }
  }

  @Override
  public void clearJoinedUsers() throws HokanServiceException {
    try {
      dao.clearJoinedUsers();
    } catch (HokanDAOException e) {
      throw new HokanServiceException(e);
    }
  }

}
