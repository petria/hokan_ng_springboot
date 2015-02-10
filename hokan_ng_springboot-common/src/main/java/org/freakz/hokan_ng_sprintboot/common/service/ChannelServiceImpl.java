package org.freakz.hokan_ng_sprintboot.common.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_sprintboot.common.exception.HokanDAOException;
import org.freakz.hokan_ng_sprintboot.common.exception.HokanException;
import org.freakz.hokan_ng_sprintboot.common.jpa.dao.ChannelDAO;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Channel;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.ChannelState;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Network;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: petria
 * Date: 11/7/13
 * Time: 2:40 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Slf4j
@Service
public class ChannelServiceImpl implements ChannelService {

  @Autowired
  private ChannelDAO channelDAO;

  @Override
  public List<Channel> findChannels(Network network, ChannelState state) {
    try {
      return channelDAO.findChannels(network, state);
    } catch (HokanException e) {
      log.error("Couldn't get Channel entity", e);
    }
    return null;
  }

  @Override
  public List<Channel> findChannels(ChannelState state) {
    try {
      return channelDAO.findChannels(state);
    } catch (HokanException e) {
      log.error("Couldn't get Channel entity", e);
    }
    return null;
  }

  @Override
  public Channel findChannelByName(Network network, String name) {
    try {
      return channelDAO.findChannelByName(network, name);
    } catch (HokanException e) {
      log.error("Couldn't get Channel entity", e);
    }
    return null;
  }

  @Override
  public Channel findChannelById(Long id) throws HokanDAOException {

    return channelDAO.findChannelById(id);
  }

  @Override
  public Channel createChannel(Network network, String name) {
    try {
      return channelDAO.createChannel(network, name);
    } catch (HokanException e) {
      log.error("Couldn't get Channel entity", e);
    }
    return null;
  }

  @Override
  public Channel updateChannel(Channel channel) {
    try {
      channel = channelDAO.updateChannel(channel);
    } catch (HokanException e) {
      log.error("Couldn't not update Channel entity", e);
    }
    return channel;
  }
}
