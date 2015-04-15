package org.freakz.hokan_ng_springboot.bot.jpa.service;

import org.freakz.hokan_ng_springboot.bot.core.HokanCoreService;
import org.freakz.hokan_ng_springboot.bot.events.IrcMessageEvent;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 15.4.2015.
 */
public interface UrlLoggerService {

  void catchUrls(IrcMessageEvent iEvent, Channel ch, HokanCoreService core);

}
