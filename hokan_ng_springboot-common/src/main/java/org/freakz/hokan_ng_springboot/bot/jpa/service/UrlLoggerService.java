package org.freakz.hokan_ng_springboot.bot.jpa.service;

import org.freakz.hokan_ng_springboot.bot.core.HokanCoreService;
import org.freakz.hokan_ng_springboot.bot.events.IrcMessageEvent;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Url;

import java.util.List;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 15.4.2015.
 *
 */
public interface UrlLoggerService {

  void catchUrls(IrcMessageEvent iEvent, Channel ch, HokanCoreService core);

  List<Url> findByUrl(String url);

  List<Url> findByUrlAndNicks(String url, String... nicks);

  List<Url> findAll();

  Url findOne(long id);

  List findTopSender();

  List findTopSenderByChannel(String channel);

}
