package org.freakz.hokan_ng_springboot.bot.service.urls;

import org.freakz.hokan_ng_springboot.bot.events.IrcMessageEvent;

/**
 * Created by Petri Airio on 27.8.2015.
 *
 */
public interface UrlCatchService {

  void catchUrls(IrcMessageEvent iEvent);

}
