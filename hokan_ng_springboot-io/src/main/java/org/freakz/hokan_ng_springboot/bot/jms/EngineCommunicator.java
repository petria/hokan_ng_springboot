package org.freakz.hokan_ng_springboot.bot.jms;

import org.freakz.hokan_ng_springboot.bot.events.IrcMessageEvent;

/**
 * Created by Petri Airio on 9.4.2015.
 *
 */
public interface EngineCommunicator {

  String sendToEngine(IrcMessageEvent event);

}
