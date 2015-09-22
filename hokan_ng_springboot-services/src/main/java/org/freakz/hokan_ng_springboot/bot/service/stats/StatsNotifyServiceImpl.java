package org.freakz.hokan_ng_springboot.bot.service.stats;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.cmdpool.CommandPool;
import org.freakz.hokan_ng_springboot.bot.cmdpool.CommandRunnable;
import org.freakz.hokan_ng_springboot.bot.enums.HokanModule;
import org.freakz.hokan_ng_springboot.bot.events.NotifyRequest;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.jms.api.JmsSender;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.PropertyName;
import org.freakz.hokan_ng_springboot.bot.jpa.service.ChannelPropertyService;
import org.freakz.hokan_ng_springboot.bot.models.StatsData;
import org.freakz.hokan_ng_springboot.bot.models.StatsMapper;
import org.freakz.hokan_ng_springboot.bot.service.StatsService;
import org.freakz.hokan_ng_springboot.bot.util.StringStuff;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
  private StatsService statsService;

  @PostConstruct
  private void startRunner() {
//    commandPool.startRunnable(this, "<system>");
  }

  private Map<String, String> sentNotifies = new HashMap<>();

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

  public String getDailyStats(Channel channel) {
    DateTime yesterday = DateTime.now().minusDays(1);
    StatsMapper statsMapper = statsService.getDailyStatsForChannel(yesterday, channel.getChannelName());

    if (!statsMapper.hasError()) {
      List<StatsData> statsDatas = statsMapper.getStatsData();
      String res = StringStuff.formatTime(yesterday.toDate(), StringStuff.STRING_STUFF_DF_DDMMYYYY )+ " word stats:";
      int i = 1;
      for (StatsData statsData : statsDatas) {
        res += " " + i + ") " + statsData.getNick() + "=" + statsData.getWords();
        i++;
      }
      return res;
    } else {
      log.warn("Could not create stats notify request!");
    }
    return null;
  }

  private void checkNotify() {
    List<Channel> channelList = channelPropertyService.getChannelsWithProperty(PropertyName.PROP_CHANNEL_DO_STATS, ".*");
    for (Channel channel : channelList) {
      String time = channelPropertyService.getChannelPropertyAsString(channel, PropertyName.PROP_CHANNEL_DO_STATS, "--");
      String timeNow = StringStuff.formatTime(new Date(), StringStuff.STRING_STUFF_DF_HHMM);
      if (timeNow.matches(time)) {
        String sentDay = StringStuff.formatTime(new Date(), StringStuff.STRING_STUFF_DF_DDMMYYYY);
        if (this.sentNotifies.get(sentDay+time) != null)  {
          continue;
        }
        String dailyStats = getDailyStats(channel);
        if (dailyStats != null) {
          NotifyRequest notifyRequest = new NotifyRequest();
          notifyRequest.setNotifyMessage(dailyStats);
          notifyRequest.setTargetChannelId(channel.getId());
          jmsSender.send(HokanModule.HokanIo.getQueueName(), "STATS_NOTIFY_REQUEST", notifyRequest, false);
          this.sentNotifies.put(sentDay+time, time);
        }
      }
    }
  }

}
