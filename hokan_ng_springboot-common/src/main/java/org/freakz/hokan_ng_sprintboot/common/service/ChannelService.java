package org.freakz.hokan_ng_sprintboot.common.service;

import java.util.List;

import org.freakz.hokan_ng_sprintboot.common.exception.HokanDAOException;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Channel;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.ChannelState;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Network;

/**
 * User: petria
 * Date: 11/7/13
 * Time: 2:41 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
public interface ChannelService {

	List<Channel> findChannels(Network network, ChannelState state);

	List<Channel> findChannels(ChannelState state);

	Channel findChannelByName(Network network, String name);

	Channel findChannelById(Long id) throws HokanDAOException;

	Channel createChannel(Network network, String name);

	Channel updateChannel(Channel channel);

}
