package org.freakz.hokan_ng_springboot.bot.jpa.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * User: petria
 * Date: 11/7/13
 * Time: 5:59 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
public enum PropertyName {

  PROP_SYS_ACCESS_CONTROL("sys.AccessControl"),
  PROP_SYS_BOT_NICK("sys.BotNick"),
  PROP_SYS_CORE_ENGINE_UPTIME("sys.CoreEngineUptime"),
  PROP_SYS_CORE_ENGINE_RUNTIME("sys.CoreEngineRuntime"),
  PROP_SYS_CORE_IO_UPTIME("sys.CoreIoUptime"),
  PROP_SYS_CORE_IO_RUNTIME("sys.CoreIoRuntime"),
  PROP_SYS_GOOGLE_API_KEY("sys.GoogleApiKey"),
  PROP_SYS_EXEC("sys.Exec"),
  PROP_SYS_HTTP_PROXY_HOST("sys.HttpProxyHost"),
  PROP_SYS_HTTP_PROXY_PORT("sys.HttpProxyPort"),
  PROP_SYS_HTTP_USER_AGENT("sys.HttpUserAgent"),
  PROP_SYS_IGNORE("sys.Ignore"),
  PROP_SYS_MAX_CONNECTION_RETRY("sys.MaxConnectionRetry"),
  PROP_SYS_RAWLOG("sys.RawLog"),

  PROP_CHANNEL_DO_JOIN_MESSAGE("channel.DoJoinMessage"),
  PROP_CHANNEL_DO_KICK_REJOIN("channel.DoKickRejoin"),
  PROP_CHANNEL_DO_TVNOTIFY("channel.DoTvNotify"),
  PROP_CHANNEL_DO_SEARCH_REPLACE("channel.DoSearchReplace"),
  PROP_CHANNEL_DO_STATS("channel.DoStats"),
  PROP_CHANNEL_DO_URL_TITLES("channel.DoUrlTitles"),
  PROP_CHANNEL_DO_WHOLELINE_TRICKERS("channel.DoWholeLineTrickers");
  private final String text;

  /**
   * @param text
   */
  private PropertyName(String text) {
    this.text = text;
  }

  public static List<PropertyName> getValuesLike(String pattern) {
    List<PropertyName> values = new ArrayList<>();
    for (PropertyName name : values()) {
      if (name.toString().toLowerCase().matches(pattern)) {
        values.add(name);
      }
    }
    return values;
  }

  /* (non-Javadoc)
   * @see java.lang.Enum#toString()
   */
  @Override
  public String toString() {
    return text;
  }
/*
  public static final String PROP_SYS_IGNORE_TITLES = "sys.IgnoreTitles";
  public static final String
  public static final String
  public static final String PROP_SYS_REPORT_TO = "sys.ReportTo";
  public static final String ;

  public static final String PROP_CHANNEL_DO_CMDS = "channel.DoCmds";
  public static final String PROP_CHANNEL_DO_JOIN_MSGS = "channel.DoJoinMsgs";
  public static final String PROP_CHANNEL_DO_LOGIN_OPS = "channel.DoLoginOps";
  public static final String PROP_CHANNEL_DO_SR = "channel.DoSR";
  public static final String PROP_CHANNEL_DO_TVNOTIFY = "channel.DoTvNotify";
  public static final String PROP_CHANNEL_DO_URL_TITLES = "channel.DoUrlTitles";
  public static final String PROP_CHANNEL_STATS_TIME = "channel.StatsTime";
  public static final String PROP_CHANNEL_TVNOTIFY_MAX_IDLE = "channel.TvNotifyMaxIdle";
  public static final String PROP_CHANNEL_WEB_CHAT_RESOLVE = "channel.WebChatResolve";
  public static final String PROP_CHANNEL_WHOLE_LINE_TRIGGERS = "channel.WholeLineTriggers";

 */

}
