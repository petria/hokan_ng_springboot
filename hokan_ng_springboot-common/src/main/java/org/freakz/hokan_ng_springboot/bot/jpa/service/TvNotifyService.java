package org.freakz.hokan_ng_springboot.bot.jpa.service;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.TvNotify;
import org.freakz.hokan_ng_springboot.bot.models.TelkkuProgram;

import java.util.Date;
import java.util.List;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 26.4.2015.
 *
 */
public interface TvNotifyService {

  TvNotify addTvNotify(Channel channel, String pattern, String owner);

  List<TvNotify> getTvNotifies(Channel channel);

  TvNotify getTvNotify(Channel channel, String pattern);

  TvNotify getTvNotifyById(long id);

  int delTvNotifies(Channel channel);

  void delTvNotify(TvNotify notify);

  List<TelkkuProgram> getChannelDailyNotifiedPrograms(Channel channel, Date day);

}
