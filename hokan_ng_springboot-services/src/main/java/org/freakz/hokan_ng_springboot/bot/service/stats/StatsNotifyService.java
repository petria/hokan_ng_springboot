package org.freakz.hokan_ng_springboot.bot.service.stats;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;

/**
 * Created by Petri Airio on 26.8.2015.
 *
 */
public interface StatsNotifyService {

  String getDailyStats(Channel channel);

}
