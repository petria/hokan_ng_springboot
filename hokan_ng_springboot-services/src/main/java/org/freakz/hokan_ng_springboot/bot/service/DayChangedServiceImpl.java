package org.freakz.hokan_ng_springboot.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.cmdpool.CommandPool;
import org.freakz.hokan_ng_springboot.bot.cmdpool.CommandRunnable;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.PropertyName;
import org.freakz.hokan_ng_springboot.bot.jpa.service.PropertyService;
import org.freakz.hokan_ng_springboot.bot.util.StringStuff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Petri Airio on 22.9.2015.
 *
 */
@Service
@Slf4j
public class DayChangedServiceImpl implements DayChangedService, CommandRunnable {

  @Autowired
  private CommandPool commandPool;

  @Autowired
  private PropertyService propertyService;

  private Map<String, String> daysDone = new HashMap<>();

  @PostConstruct
  private void startRunner() {
    commandPool.startRunnable(this, "<system>");
  }

  @Override
  public void handleRun(long myPid, Object args) throws HokanException {
    while (true) {
      try {
        checkDayChanged();
        Thread.sleep(5 * 1000);
      } catch (Exception e) {
        log.debug("interrupted");
        break;
      }
    }

  }

  private void checkDayChanged() {
    String dayChangedTime = propertyService.getPropertyAsString(PropertyName.PROP_SYS_DAY_CHANGED_TIME, "00:00");
    String time = StringStuff.formatTime(new Date(), StringStuff.STRING_STUFF_DF_HHMM);
    if (time.equals(dayChangedTime)) {
      String dayCheck = StringStuff.formatTime(new Date(), StringStuff.STRING_STUFF_DF_DDMMYYYY);
      boolean dayDone = daysDone.containsKey(dayCheck);
      if (!dayDone) {
        if (handleDayChangedTo(dayCheck)) {
          daysDone.put(dayCheck, "done");
        }
      }
    }
  }

  private boolean handleDayChangedTo(String dayChangedTo) {
    log.debug("Day changed to: {}", dayChangedTo);


    return false;
  }

}
