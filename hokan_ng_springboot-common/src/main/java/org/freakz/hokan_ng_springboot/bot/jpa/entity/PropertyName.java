package org.freakz.hokan_ng_springboot.bot.jpa.entity;

import org.freakz.hokan_ng_springboot.bot.util.StringStuff;

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
  PROP_SYS_CONFIRM_LONG_MESSAGES("sys.ConfirmLongMessages"),
  PROP_SYS_DAY_CHANGED_TIME("sys.DayChangedTime"),
  PROP_SYS_GOOGLE_API_KEY("sys.GoogleApiKey"),
  PROP_SYS_EXEC("sys.Exec"),
  PROP_SYS_HTTP_PROXY_HOST("sys.HttpProxyHost"),
  PROP_SYS_HTTP_PROXY_PORT("sys.HttpProxyPort"),
  PROP_SYS_HTTP_USER_AGENT("sys.HttpUserAgent"),
  PROP_SYS_IGNORE("sys.Ignore"),
  PROP_SYS_MAX_CONNECTION_RETRY("sys.MaxConnectionRetry"),
  PROP_SYS_PID_COUNTER("sys.PidCounter"),
  PROP_SYS_RAWLOG("sys.RawLog"),
  PROP_SYS_SESSION_ID_ENGINE("sys.SessionIdEngine"),
  PROP_SYS_SESSION_ID_IO("sys.SessionIdIo"),
  PROP_SYS_SESSION_ID_SERVICES("sys.SessionIdServices"),
  PROP_SYS_SESSION_ID_WICKET("sys.SessionIdWicket"),
  PROP_SYS_TV_XML_DATA_WATCH_DIR("sys.TvXmlDataWatchDir"),

  PROP_CHANNEL_DO_JOIN_MESSAGE("channel.DoJoinMessage"),
  PROP_CHANNEL_DO_KICK_REJOIN("channel.DoKickRejoin"),
  PROP_CHANNEL_DO_TVNOTIFY("channel.NotifyRequest"),
  PROP_CHANNEL_DO_SEARCH_REPLACE("channel.DoSearchReplace"),
  PROP_CHANNEL_DO_STATS("channel.DoStats"),
  PROP_CHANNEL_DO_URL_TITLES("channel.DoUrlTitles"),
  PROP_CHANNEL_DO_WHOLELINE_TRICKERS("channel.DoWholeLineTrickers");

  private final String text;

  /**
   *
    * @param text property name
   */
  PropertyName(String text) {
    this.text = text;
  }

  public static List<PropertyName> getValuesLike(String pattern) {
    List<PropertyName> values = new ArrayList<>();
    for (PropertyName name : values()) {
      if (StringStuff.match(name.toString(), pattern, true)) {
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
  public static final String PROP_CHANNEL_DO_TVNOTIFY = "channel.NotifyRequest";
  public static final String PROP_CHANNEL_DO_URL_TITLES = "channel.DoUrlTitles";
  public static final String PROP_CHANNEL_STATS_TIME = "channel.StatsTime";
  public static final String PROP_CHANNEL_TVNOTIFY_MAX_IDLE = "channel.TvNotifyMaxIdle";
  public static final String PROP_CHANNEL_WEB_CHAT_RESOLVE = "channel.WebChatResolve";
  public static final String PROP_CHANNEL_WHOLE_LINE_TRIGGERS = "channel.WholeLineTriggers";

 */

}
