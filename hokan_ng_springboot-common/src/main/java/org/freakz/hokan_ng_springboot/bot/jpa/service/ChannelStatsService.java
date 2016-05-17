package org.freakz.hokan_ng_springboot.bot.jpa.service;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.ChannelStats;

/**
 * Created by Petri Airio on 8.4.2015.
 * -
 */
public interface ChannelStatsService {

  ChannelStats findFirstByChannel(Channel channel);

  ChannelStats save(ChannelStats channelStats);
}
