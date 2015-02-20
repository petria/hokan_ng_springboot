package org.freakz.hokan_ng_sprintboot.repository;

import org.freakz.hokan_ng_sprintboot.entity.Channel;
import org.freakz.hokan_ng_sprintboot.entity.ChannelState;
import org.freakz.hokan_ng_sprintboot.entity.Network;

import java.util.List;

/**
 * Created by EXTAirioP on 20.2.2015.
 */
public interface ChannelService {

  List<Channel> findChannels(Network network, ChannelState joined);

}
