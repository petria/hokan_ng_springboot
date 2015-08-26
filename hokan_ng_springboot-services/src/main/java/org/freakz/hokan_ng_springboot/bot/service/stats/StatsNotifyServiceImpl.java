package org.freakz.hokan_ng_springboot.bot.service.stats;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.cmdpool.CommandPool;
import org.freakz.hokan_ng_springboot.bot.cmdpool.CommandRunnable;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.jms.api.JmsSender;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.PropertyName;
import org.freakz.hokan_ng_springboot.bot.jpa.service.ChannelPropertyService;
import org.freakz.hokan_ng_springboot.bot.jpa.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by Petri Airio on 26.8.2015.
 *
 */
@Service
@Slf4j
public class StatsNotifyServiceImpl implements StatsNotifyService, CommandRunnable {

  @Autowired
  private ChannelPropertyService channelPropertyService;

  @Autowired
  private CommandPool commandPool;

  @Autowired
  private JmsSender jmsSender;

  @Autowired
  private PropertyService propertyService;


  @PostConstruct
  private void startRunner() {
    commandPool.startRunnable(this, "<system>");
  }

  @Override
  public void handleRun(long myPid, Object args) throws HokanException {
    while (true) {
      try {
        checkNotify();
        Thread.sleep(30 * 1000);
      } catch (Exception e) {
        log.debug("interrupted");
        break;
      }
    }
  }

  private void checkNotify() {
    List<Channel> channelList = channelPropertyService.getChannelsWithProperty(PropertyName.PROP_CHANNEL_DO_STATS, ".*");
    for (Channel channel : channelList) {
      String time = channelPropertyService.getChannelPropertyAsString(channel, PropertyName.PROP_CHANNEL_DO_STATS, "--");
      
    }
  }

}
