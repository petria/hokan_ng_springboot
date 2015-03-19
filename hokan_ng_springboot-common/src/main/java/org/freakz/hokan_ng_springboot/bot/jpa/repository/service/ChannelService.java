package org.freakz.hokan_ng_springboot.bot.jpa.repository.service;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.ChannelState;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Network;

import java.util.List;

/**
 *
 * Created by EXTAirioP on 20.2.2015.
 */
public interface ChannelService {

  List<Channel> findChannels(Network network, ChannelState joined);

  List<Channel> findAll();

}
