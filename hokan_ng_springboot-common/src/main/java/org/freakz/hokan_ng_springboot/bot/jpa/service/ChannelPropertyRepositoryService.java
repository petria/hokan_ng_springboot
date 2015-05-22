package org.freakz.hokan_ng_springboot.bot.jpa.service;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.ChannelProperty;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.PropertyName;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.ChannelPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petri Airio on 26.3.2015.
 *
 */
@Service
public class ChannelPropertyRepositoryService implements ChannelPropertyService {

  @Autowired
  private ChannelPropertyRepository repository;

  @Override
  public List<ChannelProperty> findByChannel(Channel channel) {
    return repository.findByChannel(channel);
  }

  @Override
  @Transactional
  public ChannelProperty save(ChannelProperty newRow) {
    return repository.save(newRow);
  }

  @Override
  @Transactional
  public void delete(ChannelProperty object) {
    repository.delete(object);
  }

  @Override
  @Transactional
  public void deleteByChannel(Channel object) {
    repository.deleteByChannel(object);
  }

  public List<Channel> getChannelsWithProperty(PropertyName propertyName) {
    List<ChannelProperty> properties = repository.findByPropertyName(propertyName);
    List<Channel> channels = new ArrayList<>();
    for (ChannelProperty property : properties) {
      channels.add(property.getChannel());
    }
    return channels;
  }

  @Override
  public ChannelProperty setChannelProperty(Channel theChannel, PropertyName propertyName, String value) {
    ChannelProperty property = repository.findFirstByChannelAndPropertyName(theChannel, propertyName);
    if (property == null) {
      property = new ChannelProperty(theChannel, propertyName, value, "");
    } else {
      property.setValue(value);
    }
    return repository.save(property);
  }

}
