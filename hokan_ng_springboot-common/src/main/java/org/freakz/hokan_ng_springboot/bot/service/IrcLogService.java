package org.freakz.hokan_ng_springboot.bot.service;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.IrcLog;

import java.util.Date;

/**
 * Created by Petri Airio on 21.8.2015.
 *
 */
public interface IrcLogService {

  IrcLog addIrcLog(Date timeStamp, String sender, String target, String message);


}
