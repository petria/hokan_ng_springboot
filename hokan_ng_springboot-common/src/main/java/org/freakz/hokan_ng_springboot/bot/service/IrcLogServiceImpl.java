package org.freakz.hokan_ng_springboot.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.IrcLog;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.IrcLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Petri Airio on 21.8.2015.
 *
 */
@Service
@Slf4j
public class IrcLogServiceImpl implements IrcLogService {

  @Autowired
  private IrcLogRepository repository;

  @Override
  public IrcLog addIrcLog(Date timeStamp, String sender, String target, String message) {
    IrcLog log = new IrcLog();
    log.setTimeStamp(timeStamp);
    log.setSender(sender);
    log.setTarget(target);
    log.setMessage(message);
    return repository.save(log);
  }

}
