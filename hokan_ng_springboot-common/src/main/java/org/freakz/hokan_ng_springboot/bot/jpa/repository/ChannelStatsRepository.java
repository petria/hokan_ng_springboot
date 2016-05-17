package org.freakz.hokan_ng_springboot.bot.jpa.repository;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.ChannelStats;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Petri Airio on 8.4.2015.
 * -
 */
public interface ChannelStatsRepository extends JpaRepository<ChannelStats, Long> {


  ChannelStats findFirstByChannel(Channel channel);

}
