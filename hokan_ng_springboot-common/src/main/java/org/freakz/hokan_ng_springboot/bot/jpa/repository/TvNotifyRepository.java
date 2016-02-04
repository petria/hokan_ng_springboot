package org.freakz.hokan_ng_springboot.bot.jpa.repository;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.TvNotify;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 26.4.2015.
 *
 */
public interface TvNotifyRepository extends JpaRepository<TvNotify, Long> {

  List<TvNotify> findByChannel(Channel channel);

  TvNotify findFirstByChannelAndNotifyPattern(Channel channel, String notifyPattern);

}
