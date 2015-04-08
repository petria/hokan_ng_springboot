package org.freakz.hokan_ng_springboot.bot.jpa.repository.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.ChannelStats;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.ChannelStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Petri Airio on 8.4.2015.
 */
@Service
@Slf4j
public class ChannelStatsRepositoryService implements ChannelStatsService {

  @Autowired
  private ChannelStatsRepository repository;

  @Override
  public ChannelStats findFirstByChannel(Channel channel) {
    return repository.findFirstByChannel(channel);
  }

  @Override
  public ChannelStats save(ChannelStats channelStats) {
    return repository.save(channelStats);
  }

}
