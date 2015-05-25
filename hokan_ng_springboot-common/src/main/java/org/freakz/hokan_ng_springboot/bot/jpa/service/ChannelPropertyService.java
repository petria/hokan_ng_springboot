package org.freakz.hokan_ng_springboot.bot.jpa.service;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.ChannelProperty;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.PropertyName;

import java.util.List;

/**
 * Created by Petri Airio on 26.3.2015.
 *
 */
public interface ChannelPropertyService {

  List<ChannelProperty> findByChannel(Channel channel);

  ChannelProperty save(ChannelProperty newRow);

  void delete(ChannelProperty object);

  void deleteByChannel(Channel object);

  List<Channel> getChannelsWithProperty(PropertyName propChannelDoTvnotify, String valueMatcher);

  ChannelProperty setChannelProperty(Channel theChannel, PropertyName propertyName, String value);

}
