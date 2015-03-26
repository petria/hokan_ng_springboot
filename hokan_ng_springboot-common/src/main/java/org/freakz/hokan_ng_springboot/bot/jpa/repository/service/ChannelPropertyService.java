package org.freakz.hokan_ng_springboot.bot.jpa.repository.service;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.ChannelProperty;

import java.util.List;

/**
 * Created by Petri Airio on 26.3.2015.
 */
public interface ChannelPropertyService {

  List<ChannelProperty> findByChannel(Channel channel);

}
