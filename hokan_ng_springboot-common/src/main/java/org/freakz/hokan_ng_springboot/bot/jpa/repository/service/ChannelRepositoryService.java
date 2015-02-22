package org.freakz.hokan_ng_springboot.bot.jpa.repository.service;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.ChannelState;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Network;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by JohnDoe on 22.2.2015.
 */
@Service
public class ChannelRepositoryService implements ChannelService {

  @Autowired
  private ChannelRepository repository;

  @Override
  public List<Channel> findChannels(Network network, ChannelState channelState) {
    return repository.findByNetworkAndChannelState(network, channelState);
  }

  @Override
  public List<Channel> findAll() {
    return repository.findAll();
  }

}
