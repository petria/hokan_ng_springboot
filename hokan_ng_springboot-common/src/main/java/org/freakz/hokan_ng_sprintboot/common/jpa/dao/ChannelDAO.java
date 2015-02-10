package org.freakz.hokan_ng_sprintboot.common.jpa.dao;

import org.freakz.hokan_ng_sprintboot.common.exception.HokanDAOException;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Channel;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.ChannelState;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Network;

import java.util.List;

/**
 * User: petria
 * Date: 11/7/13
 * Time: 1:45 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
public interface ChannelDAO {

  Channel createChannel(Network network, String name) throws HokanDAOException;

  Channel findChannelByName(Network network, String name) throws HokanDAOException;

  Channel findChannelById(Long id) throws HokanDAOException;

  List<Channel> findChannels(Network network, ChannelState state) throws HokanDAOException;

  List<Channel> findChannels(ChannelState state) throws HokanDAOException;

  Channel updateChannel(Channel channel) throws HokanDAOException;

}
