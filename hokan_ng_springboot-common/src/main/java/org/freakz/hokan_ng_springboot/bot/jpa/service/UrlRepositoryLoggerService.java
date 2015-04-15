package org.freakz.hokan_ng_springboot.bot.jpa.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.core.HokanCoreService;
import org.freakz.hokan_ng_springboot.bot.events.IrcMessageEvent;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Url;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.UrlRepository;
import org.freakz.hokan_ng_springboot.bot.util.StringStuff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 15.4.2015.
 */
@Service
@Slf4j
public class UrlRepositoryLoggerService implements UrlLoggerService {

  @Autowired
  private UrlRepository repository;


  public long logUrl(IrcMessageEvent iEvent, String url) {
    long isWanha = 0;


    Url entity = repository.findByUrlLikeOrUrlTitleLikeOrderByCreatedDesc(url, url);
    if (entity != null) {
      entity.addWanhaCount(1);
      isWanha = entity.getWanhaCount();
    } else {
      entity = new Url(url, iEvent.getSender(), iEvent.getChannel(), new Date());
    }
    repository.save(entity);
    log.info("Logged URL: {}", entity);

// TODO   StatsHandler.getInstance().handleChannelUrlStats(iEvent, isWanha > 0);

    return isWanha;
  }


  @Override
  public void catchUrls(IrcMessageEvent iEvent, Channel ch, HokanCoreService core) {
    String msg = iEvent.getMessage();
    String regexp = "(https?://|www\\.)\\S+";

    Pattern p = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
    Matcher m = p.matcher(msg);
    while (m.find()) {
      String url = m.group();
      long isWanha = logUrl(iEvent, url);
      String ignoreTitles = "fbcdn-sphotos.*|.*(avi|bz|gz|gif|exe|iso|jpg|jpeg|mp3|mp4|mkv|mpeg|mpg|mov|pdf|png|torrent|zip|7z|tar)";

      String wanhaAdd = "";
      for (int i = 0; i < isWanha; i++) {
        wanhaAdd += "!";
      }
      if (!StringStuff.match(url, ignoreTitles, true)) {
        log.info("Finding title: " + url);
// TODO        getTitle(iEvent, ch, url, "utf-8", isWanha > 0, wanhaAdd, core);
      } else {
        log.info("SKIP finding title: " + url);
        if (isWanha > 0) {
          processReply(iEvent, url + " | wanha" + wanhaAdd, core);
        }
      }
    }
  }

  private void processReply(IrcMessageEvent iEvent, String reply, HokanCoreService core) {
    core.handleSendMessage(iEvent.getChannel(), reply, true, null, null);
  }


}
