package org.freakz.hokan_ng_springboot.bot.jpa.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.cmdpool.CommandPool;
import org.freakz.hokan_ng_springboot.bot.cmdpool.CommandRunnable;
import org.freakz.hokan_ng_springboot.bot.core.HokanCoreService;
import org.freakz.hokan_ng_springboot.bot.events.IrcMessageEvent;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.PropertyName;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Url;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.UrlRepository;
import org.freakz.hokan_ng_springboot.bot.util.HttpPageFetcher;
import org.freakz.hokan_ng_springboot.bot.util.StringStuff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 15.4.2015.
 */
@Service
@Slf4j
public class UrlRepositoryLoggerService implements UrlLoggerService {

  @Autowired
  ApplicationContext context;

  @Autowired
  private CommandPool commandPool;

  @Autowired
  private ChannelPropertyService channelPropertyService;

  @Autowired
  private UrlRepository repository;

  static class HTMLEditorKit2 extends HTMLEditorKit {
    public Document createDefaultDocument() {
      HTMLDocument doc = (HTMLDocument) (super.createDefaultDocument());
      doc.setAsynchronousLoadPriority(-1); // load synchronously
      return doc;
    }
  }


  public void getTitle(final IrcMessageEvent iEvent, final Channel ch, final String url, final String encoding, final boolean isWanha, final String wanhaAadd, final HokanCoreService core) {

    CommandRunnable cmdRunnable = new CommandRunnable() {

      public void handleRun(long myPid, Object args) {
        String title = null;
        try {

          JEditorPane editorPane = new JEditorPane();
          JEditorPane.registerEditorKitForContentType
              ("text/html", "HTMLEditorKit2");
          editorPane.setEditorKitForContentType
              ("text/html", new HTMLEditorKit2());
          editorPane.setPage(new URL(url));
          title = (String) editorPane.getDocument().getProperty("title");

        } catch (Exception ioe) {
          log.info("Reverting to old <title> finding method: " + ioe.getMessage());
          try {
            final String START_TAG = "<title>";
            final String END_TAG = "</title>";

            HttpPageFetcher page = context.getBean(HttpPageFetcher.class);
            page.fetch(url, encoding);

            String html = page.getHtmlBuffer().toString().replaceAll("\n|\r", "");
            html = html.replaceAll("<TITLE>", "<title>");
            html = html.replaceAll("</TITLE>", "</title>");
            int idx1 = html.indexOf(START_TAG);
            int idx2 = html.indexOf(END_TAG, idx1);
            title = html.substring(idx1 + START_TAG.length(), idx2);
          } catch (Exception e) {
            log.error("Urls", e);
          }
        }

        if (title != null) {
          title = StringStuff.htmlEntitiesToText(title);
          title = title.replaceAll("\t", "");


          Url entity = repository.findFirstByUrlLikeOrUrlTitleLikeOrderByCreatedDesc(url, url);

          entity.setUrlTitle(title);
          repository.save(entity);


          if (url.contains("http://www.imdb.com/title/")) {
            try {
              String ratings = getIMDBData(url);
              if (ratings != null) {
                title += " | " + ratings;
              }
            } catch (Exception e) {
              e.printStackTrace();
            }
          }

          if (isWanha) {
            title = title + " | wanha" + wanhaAadd;
          }
          boolean titles = channelPropertyService.getChannelPropertyAsBoolean(ch, PropertyName.PROP_CHANNEL_DO_URL_TITLES, false);
          if (titles) {
            processReply(iEvent, title, core);
          }
        } else {
          log.info("Could not find title for url: " + url);
        }

      }
    };
    commandPool.startRunnable(cmdRunnable, "<system>");

  }

  public String getIMDBData(String url) throws Exception {
    HttpPageFetcher page = context.getBean(HttpPageFetcher.class);
    page.fetch(url, "UTF-8");

    String html = page.getHtmlBuffer().toString().replaceAll("\n|\r", "");
    Pattern pattern = Pattern.compile("<span itemprop=\"ratingValue\">(.*?)</span>.*<span itemprop=\"ratingCount\">(.*?)</span> users</a>");
    Matcher matcher = pattern.matcher(html);
    String ratings = null;
    if (matcher.find()) {
      String rating = matcher.group(1);
      String users = matcher.group(2);
      ratings = String.format("Ratings: %s/10 from %s users", rating, users);
    }
    return ratings;
  }

  public long logUrl(IrcMessageEvent iEvent, String url) {
    long isWanha = 0;


    Url entity = repository.findFirstByUrlLikeOrUrlTitleLikeOrderByCreatedDesc(url, url);
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
        getTitle(iEvent, ch, url, "utf-8", isWanha > 0, wanhaAdd, core);
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

  @Override
  public List<Url> findByUrl(String url) {
    String likeUrl = "%" + url + "%";
    return repository.findByUrlLikeOrUrlTitleLikeOrderByCreatedDesc(likeUrl, likeUrl);
  }

  @Override
  public List<Url> findByUrlAndNicks(String url, String... nicks) {
    List<String> nickList = Arrays.asList(nicks);
    String likeUrl = "%" + url + "%";
    return repository.findByUrlLikeOrUrlTitleLikeAndSenderInOrderByCreatedDesc(likeUrl, likeUrl, nickList);
  }

  @Override
  public List<Url> findAll() {
    return repository.findAll();
  }

  @Override
  public Url findOne(long id) {
    return repository.findOne(id);
  }

  @Override
  public List findTopSender() {
    return repository.findTopSender();
  }

  @Override
  public List findTopSenderByChannel(String channel) {
    return repository.findTopSenderByChannel(channel);
  }
}
