package org.freakz.hokan_ng_springboot.bot.jpa.repository.service;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.ChannelProperty;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.ChannelPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Petri Airio on 26.3.2015.
 */
@Service
public class ChannelPropertyRepositoryService implements ChannelPropertyService {

  @Autowired
  private ChannelPropertyRepository repository;

  @Override
  public List<ChannelProperty> findByChannel(Channel channel) {
    return repository.findByChannel(channel);
  }

}
