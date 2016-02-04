package org.freakz.hokan_ng_springboot.bot.jpa.repository;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.ChannelPropertyEntity;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.PropertyName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Petri Airio on 26.3.2015.
 *
 */
public interface ChannelPropertyRepository extends JpaRepository<ChannelPropertyEntity, Long> {

  List<ChannelPropertyEntity> findByChannel(Channel channel);

  void deleteByChannel(Channel object);

  List<ChannelPropertyEntity> findByPropertyName(PropertyName propertyName);

  ChannelPropertyEntity findFirstByChannelAndPropertyName(Channel theChannel, PropertyName propertyName);

}
