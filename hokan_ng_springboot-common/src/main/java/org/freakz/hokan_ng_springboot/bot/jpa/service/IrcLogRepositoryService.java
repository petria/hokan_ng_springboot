package org.freakz.hokan_ng_springboot.bot.jpa.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.IrcLog;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.IrcLogRepository;
import org.freakz.hokan_ng_springboot.bot.models.StartAndEndTime;
import org.freakz.hokan_ng_springboot.bot.util.StringStuff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Petri Airio on 21.8.2015.
 *
 */
@Service
@Slf4j
public class IrcLogRepositoryService implements IrcLogService {

  @Autowired
  private IrcLogRepository repository;

  @Override
  @Transactional(readOnly = false)
  public IrcLog addIrcLog(Date timeStamp, String sender, String target, String message) {
    IrcLog log = new IrcLog();
    log.setTimeStamp(timeStamp);
    log.setSender(sender);
    log.setTarget(target);
    log.setMessage(message);
    return repository.save(log);
  }

  @Override
  @Transactional(readOnly = true)
  public List<IrcLog> findMatchingLogRows(String logPattern) {
    List<IrcLog> allRows = repository.findAll();
    List<IrcLog> matching = new ArrayList<>();
    for (IrcLog log : allRows) {
      String logString = log.toString();
      if (StringStuff.match(logString, ".*" + logPattern + ".*", true)) {
        matching.add(log);
      }
    }
    return sortLogs(matching);
  }

  @Override
  @Transactional(readOnly = true)
  public List<IrcLog> findByTimeStampBetween(StartAndEndTime saet) {
    return repository.findByTimeStampBetween(saet.getStartTime().toDate(), saet.getEndTime().toDate());
  }

  @Override
  public List<IrcLog> findByTimeStampBetweenAndTargetContaining(StartAndEndTime saet, String target) {
    return repository.findByTimeStampBetweenAndTargetContaining(saet.getStartTime().toDate(), saet.getEndTime().toDate(), target);
  }

  private List<IrcLog> sortLogs(List<IrcLog> allRows) {
    Comparator<? super IrcLog> comparator = (o1, o2) -> o1.getTimeStamp().compareTo(o2.getTimeStamp());
    Collections.sort(allRows, comparator);
    return allRows;
  }

  @Override
  public List<IrcLog> findByTarget(String target) {
    return repository.findByTarget(target);
  }
}
