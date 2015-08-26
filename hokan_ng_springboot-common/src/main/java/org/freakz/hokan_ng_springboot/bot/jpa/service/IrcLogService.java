package org.freakz.hokan_ng_springboot.bot.jpa.service;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.IrcLog;
import org.freakz.hokan_ng_springboot.bot.models.StartAndEndTime;

import java.util.Date;
import java.util.List;

/**
 * Created by Petri Airio on 21.8.2015.
 *
 */
public interface IrcLogService {

  IrcLog addIrcLog(Date timeStamp, String sender, String target, String message);

  List<IrcLog> findMatchingLogRows(String logPattern);

  List<IrcLog> findByTimeStampBetween(StartAndEndTime startAndEndTime);

  List<IrcLog> findByTimeStampBetweenAndTargetContaining(StartAndEndTime startAndEndTime, String target);

  List<IrcLog> findByTarget(String target);

}
