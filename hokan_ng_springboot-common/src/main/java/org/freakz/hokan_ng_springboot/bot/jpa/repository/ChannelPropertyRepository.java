package org.freakz.hokan_ng_springboot.bot.jpa.repository;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.ChannelProperty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Petri Airio on 26.3.2015.
 *
 */
public interface ChannelPropertyRepository extends JpaRepository<ChannelProperty, Long> {

  List<ChannelProperty> findByChannel(Channel channel);

  void deleteByChannel(Channel object);

}
