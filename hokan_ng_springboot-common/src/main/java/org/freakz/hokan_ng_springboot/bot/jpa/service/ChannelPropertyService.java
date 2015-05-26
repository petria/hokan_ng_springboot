package org.freakz.hokan_ng_springboot.bot.jpa.service;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.ChannelPropertyEntity;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.PropertyName;

import java.util.List;

/**
 * Created by Petri Airio on 26.3.2015.
 *
 */
public interface ChannelPropertyService {

  ChannelPropertyEntity findFirstByChannelAndPropertyName(Channel channel, PropertyName propertyName);

  List<ChannelPropertyEntity> findByChannel(Channel channel);

  ChannelPropertyEntity save(ChannelPropertyEntity newRow);

  void delete(ChannelPropertyEntity object);

  void deleteByChannel(Channel object);

  List<Channel> getChannelsWithProperty(PropertyName propChannelDoTvnotify, String valueMatcher);

  ChannelPropertyEntity setChannelProperty(Channel theChannel, PropertyName propertyName, String value);

  boolean getChannelPropertyAsBoolean(Channel channel, PropertyName propertyName, boolean b);

}
