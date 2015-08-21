package org.freakz.hokan_ng_springboot.bot.ircengine;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.core.HokanCoreService;
import org.freakz.hokan_ng_springboot.bot.events.*;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.ircengine.connector.EngineConnector;
import org.freakz.hokan_ng_springboot.bot.jms.EngineCommunicator;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.*;
import org.freakz.hokan_ng_springboot.bot.jpa.service.*;
import org.freakz.hokan_ng_springboot.bot.service.IrcLogService;
import org.freakz.hokan_ng_springboot.bot.util.IRCUtility;
import org.freakz.hokan_ng_springboot.bot.util.StringStuff;
import org.jibble.pircbot.PircBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by AirioP on 17.2.2015.
 *
 */
@Component
@Scope("prototype")
@Slf4j
public class HokanCore extends PircBot implements HokanCoreService {

  @Autowired
  private ChannelService channelService;

  @Autowired
  private ChannelStatsService channelStatsService;

  @Autowired
  private EngineCommunicator engineCommunicator;

  @Autowired
  private IrcLogService ircLogService;

  @Autowired
  private JoinedUserService joinedUsersService;

  @Autowired
  private NetworkService networkService;

  @Autowired
  private UserChannelService userChannelService;

  @Autowired
  private UrlLoggerService urlLoggerService;

  @Autowired
  private UserService userService;

  @Autowired
  private OutputQueue outputQueue;

  private EngineConnector engineConnector;
  private IrcServerConfig ircServerConfig;
  private Map<String, String> serverProperties = new HashMap<>();

  private Map<String, List<String>> whoQueries = new HashMap<>();

  public void init(String botName, IrcServerConfig ircServerConfig) {
    this.ircServerConfig = ircServerConfig;
    setVerbose(true);
    setName(botName);
    setVersion("Hokan NG");
    setLogin("hokan");
    setMessageDelay(1100);
  }

  public IrcServerConfig getIrcServerConfig() {
    return this.ircServerConfig;
  }

  public void setIrcServerConfig(IrcServerConfig ircServerConfig) {
    this.ircServerConfig = ircServerConfig;
  }

  public void startOutputQueue() {
    this.outputQueue.init(this, getIrcServerConfig().isThrottleInUse());
  }

  @Override
  public void dispose() {
    outputQueue.stop();
    List<Runnable> runnableList = executor.shutdownNow();
    log.info("Runnables size: {}", runnableList.size());
    super.dispose();
  }

  @Autowired
  public void setEngineConnector(EngineConnector engineConnector) {
    this.engineConnector = engineConnector;
  }

  public void log(String message) {
    if (!message.contains("PING") && !message.contains("PONG")) {
      log.info(message);
    }
  }

  @Override
  protected void onUnknown(String line) {
    log.info("UNKNOWN: {}", line);
    if (line.contains("Ping timeout")) {
      this.engineConnector.engineConnectorPingTimeout(this);
    } else if (line.toLowerCase().contains("excess flood")) {
      this.engineConnector.engineConnectorExcessFlood(this);
    }
  }

  public void sendWhoQuery(String channel) {
    log.info("Sending WHO query to: " + channel);
    List<String> whoReplies = new ArrayList<>();
    whoQueries.put(channel.toLowerCase(), whoReplies);
    sendRawLineViaQueue("WHO " + channel);
  }

  @Override
  protected void onUserList(String channel, org.jibble.pircbot.User[] users) {
    sendWhoQuery(channel);
  }

  public Network getNetwork() {
    return networkService.getNetwork(getIrcServerConfig().getNetwork().getName());
  }

  public Channel getChannel(String channelName) {
    Channel channel;
    channel = channelService.findByNetworkAndChannelName(getNetwork(), channelName);

    if (channel == null) {
      channel = channelService.createChannel(getNetwork(), channelName);
    }
    return channel;
  }

  public Channel getChannel(IrcEvent ircEvent) {
    return getChannel(ircEvent.getChannel());
  }

  public ChannelStats getChannelStats(Channel channel) {
    ChannelStats channelStats = channelStatsService.findFirstByChannel(channel);
    if (channelStats == null) {
      channelStats = new ChannelStats();
      channelStats.setChannel(channel);
    }
    return channelStats;
  }

  public User getUser(IrcEvent ircEvent) {
    User user;
    User maskUser = this.userService.getUserByMask(ircEvent.getMask());
    if (maskUser != null) {
      user = maskUser;
    } else {
      user = this.userService.findFirstByNick(ircEvent.getSender());
      if (user == null) {
        user = new User(ircEvent.getSender());
        user = userService.save(user);
      }
    }
    user.setRealMask(StringStuff.quoteRegExp(ircEvent.getMask()));
    this.userService.save(user);
    return user;
  }

  private void handleWhoList(String channelName, List<String> whoReplies) throws HokanException {
    Channel channel = getChannel(channelName);
    ChannelStats channelStats = getChannelStats(channel);
    if (whoReplies.size() > channelStats.getMaxUserCount()) {
      channelStats.setMaxUserCount(whoReplies.size());
      channelStats.setMaxUserCountDate(new Date());
    }
    channelStats.setLastActive(new Date());
    channelStatsService.save(channelStats);

    this.joinedUsersService.clearJoinedUsers(channel);
    for (String whoLine : whoReplies) {
      String[] split = whoLine.split(" ");
      String nick = split[5];
      String mask = split[5] + "!" + split[2] + "@" + split[3];
      String userModes = split[6];
      String fullName = StringStuff.joinStringArray(split, 8);
      User user = this.userService.findFirstByNick(nick);
      if (user == null) {
        user = new User();
        user.setNick(split[5]);
        user.setMask(StringStuff.quoteRegExp(mask));
        user.setPassword("not_set");
        user.setFullName(fullName);
      }
      user.setRealMask(StringStuff.quoteRegExp(mask));
      user = this.userService.save(user);

      UserChannel userChannel = userChannelService.getUserChannel(user, channel);
      if (userChannel == null) {
        userChannelService.createUserChannel(user, channel);
      }
      this.joinedUsersService.createJoinedUser(channel, user, userModes);
    }
  }

  @Override
  protected void onOp(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient) {
    sendWhoQuery(channel);
  }

  @Override
  protected void onDeop(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient) {
    sendWhoQuery(channel);
  }

  @Override
  protected void onVoice(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient) {
    sendWhoQuery(channel);
  }

  @Override
  protected void onDeVoice(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient) {
    sendWhoQuery(channel);
  }

  @Override
  protected void onServerResponse(int code, String line) {
    if (code == RPL_WHOREPLY) {
      String[] split = line.split(" ");
      if (split.length >= 6) {
        String channel = split[1];
        List<String> whoReplies = whoQueries.get(channel.toLowerCase());
        whoReplies.add(line);
      } else {
        log.info("SKIPPED WHO REPLY: {}", line);
      }

    } else if (code == RPL_ENDOFWHO) {
      String[] split = line.split(" ");
      String channel = split[1];
      List<String> whoReplies = this.whoQueries.remove(channel.toLowerCase());
      try {
        handleWhoList(channel, whoReplies);
      } catch (HokanException e) {
        log.error("Core error", e);
      }
      log.info("Handled {} WHO lines!", whoReplies.size());

    }
    if (code == 5) {
      String[] split = line.split(" ");
      for (String str : split) {
        if (str.contains("=")) {
          String[] keyValue = str.split("=");
          this.serverProperties.put(keyValue[0], keyValue[1]);
          log.info("--> {}: {}", keyValue[0], keyValue[1]);
        }
      }
    }
  }

  private boolean isBotOp(Channel channel) {
    for (JoinedUser user : joinedUsersService.findJoinedUsers(channel)) {
      if (user.getUser().getNick().equalsIgnoreCase(getName())) {
        return user.isOp();
      }
    }
    return false;
  }

  @Override
  protected void onPrivateMessage(String sender, String login, String hostname, String message) {
    this.ircLogService.addIrcLog(new Date(), sender, getName(), message);
  }

  @Override
  protected void onMessage(String channel, String sender, String login, String hostname, String message) {
    this.ircLogService.addIrcLog(new Date(), sender, channel, message);
    String toMe = getName() + ": ";
    boolean isToMe = false;
    if (message.startsWith(toMe)) {
      message = message.replaceFirst(toMe, "");
      isToMe = true;
    }
    IrcMessageEvent ircEvent = (IrcMessageEvent) IrcEventFactory.createIrcMessageEvent(getName(), getNetwork().getName(), channel, sender, login, hostname, message);
    ircEvent.setToMe(isToMe);

    Network nw = getNetwork();
    nw.addToLinesReceived(1);
    this.networkService.save(nw);

    User user = getUser(ircEvent);
    Channel ch = getChannel(ircEvent);
    ircEvent.setBotOp(isBotOp(ch));

    ChannelStats channelStats = channelStatsService.findFirstByChannel(ch);
    if (channelStats == null) {
      channelStats = new ChannelStats();
      channelStats.setChannel(ch);
    }

    channelStats.setLastActive(new Date());
    channelStats.setLastMessage(ircEvent.getMessage());
    channelStats.setLastWriter(ircEvent.getSender());
    channelStats.addToLinesReceived(1);

    String lastWriter = channelStats.getLastWriter();
    if (lastWriter != null && lastWriter.equalsIgnoreCase(sender)) {
      int spree = channelStats.getLastWriterSpree();
      spree++;
      channelStats.setLastWriterSpree(spree);
      if (spree > channelStats.getWriterSpreeRecord()) {
        channelStats.setWriterSpreeRecord(spree);
        channelStats.setWriterSpreeOwner(sender);
      }
    } else {
      channelStats.setLastWriterSpree(1);
    }

    channelStatsService.save(channelStats);

    UserChannel userChannel = userChannelService.getUserChannel(user, ch);
    if (userChannel == null) {
      userChannel = new UserChannel(user, ch);
    }
    userChannel.setLastMessage(message);
    userChannel.setLastMessageTime(new Date());
    userChannelService.save(userChannel);

    urlLoggerService.catchUrls(ircEvent, ch, this);

/*
    boolean wlt = properties.getChannelPropertyAsBoolean(ch, PropertyName.PROP_CHANNEL_DO_WHOLELINE_TRICKERS, false);
    if (wlt || ircEvent.isToMe()) {
      WholeLineTrickers wholeLineTrickers = new WholeLineTrickers(this);
      wholeLineTrickers.checkWholeLineTrickers(ircEvent);
    }

    if (accessControlService.isMasterUser(ircEvent)) {
      handleBuiltInCommands(ircEvent);
    }
*/
    this.channelService.save(ch);
    boolean ignore = false;
    String flags = user.getFlags();
    if (flags != null) {
      if (flags.contains("I")) {
        ignore = true;
      }
    }
    if (ignore) {
      log.debug("Ignoring: {}", user);
    } else {
      String result = engineCommunicator.sendToEngine(ircEvent);
      log.info(">>> sent to engine: {}", result);
    }
  }

  private Map<String, Method> methodMap = null;

  private void buildMethodMap() {
    Class clazz = HokanCore.class;
    Method[] methods = clazz.getMethods();
    this.methodMap = new HashMap<>();
    for (Method method : methods) {
      methodMap.put(method.getName(), method);
    }
    log.info("Built method map, size {}", methodMap.size());

  }

  private Method getEngineMethod(String name) { //}, int args) {
    if (this.methodMap == null) {
      buildMethodMap();
    }
    List<Method> matches = new ArrayList<>();
    for (Method method : methodMap.values()) {
      if (method.getName().equals(name)) { // && method.getGenericParameterTypes().length == args) {
        matches.add(method);
      }
    }
    if (matches.size() == 1) {
      return matches.get(0);
    } else if (matches.size() > 1) {
      log.info("ffufu"); // TODO
      return matches.get(0);
    }
    return null;
  }


  //  @Override
  @SuppressWarnings({"ThrowableResultOfMethodCallIgnored"})
  public void handleEngineResponse(EngineResponse response) {
    log.debug("Handle: {}", response);

    if (response.getException() != null) {
      String error = " failed: " + response.getException().getMessage();
      String message = response.getIrcMessageEvent().getSender() + ": " + error;
      String target = response.getIrcMessageEvent().getChannel();
      sendMessage(target, message);
      return;
    }

    handleSendMessage(response);
/*    if (response.getCommandClass() != null) {
      Channel ch = getChannel(response.getRequest().getIrcEvent().getChannel());
      ch.addCommandsHandled(1);
      this.channelService.updateChannel(ch);
    }
TODO
*/
    for (EngineMethodCall methodCall : response.getEngineMethodCalls()) {
      String methodName = methodCall.getMethodName();
      String[] methodArgs = methodCall.getMethodArgs();

      log.info("Executing engine method : " + methodName);
      log.info("Engine method args      : " + StringStuff.arrayToString(methodArgs, ", "));
      Method method = getEngineMethod(methodName);
      if (method != null) {
        String[] args = new String[method.getParameterTypes().length];
        int i = 0;
        for (String arg : methodArgs) {
          args[i] = arg;
          i++;
        }
        log.info("Using method args       : " + StringStuff.arrayToString(args, ", "));
        try {
          log.info("Invoking method         : {}", method);
          Object result = method.invoke(this, (Object[]) args);
          log.info("Invoke   result         : {}", result);
        } catch (Exception e) {
          log.error("Couldn't do engine method!", e);
        }
      } else {
        log.error("Couldn't find method for: " + methodName);
      }

    }
  }

  protected void handleSendMessage(EngineResponse response) {
    String channel = response.getReplyTo();
    String message = response.getResponseMessage();
    if (message != null && message.trim().length() > 1) {
      boolean doSr = false;
      if (!response.isNoSearchReplace()) {
        if (!response.getIrcMessageEvent().isPrivate()) {
          Channel ch = getChannel(response.getIrcMessageEvent().getChannel());
//          doSr = properties.getChannelPropertyAsBoolean(ch, PropertyName.PROP_CHANNEL_DO_SEARCH_REPLACE, false);
        }
      }
      handleSendMessage(channel, message, doSr, "", "");
    }
  }

  private String handleSearchReplace(String message) {
/*    List<SearchReplace> searchReplaces = searchReplaceService.getSearchReplaces();
    for (SearchReplace sr : searchReplaces) {
      try {
        message = Pattern.compile(sr.getSearch(), Pattern.CASE_INSENSITIVE).matcher(message).replaceAll(sr.getReplace());
      } catch (Exception e) {
        message += " || SR error: " + sr.getId() + " || ";
        break;
      }
    }
    TODO
    */
    return message;
  }

  public void handleSendMessage(String channel, String message) {
    handleSendMessage(channel, message, false, null, null);
  }

  public void handleSendMessage(String channel, String message, boolean doSr, String prefix, String postfix) {

    if (doSr) {
      message = handleSearchReplace(message);
    }

    Channel ch = null;
    if (channel.startsWith("#")) {
      ch = getChannel(channel);
    }
    if (prefix == null) {
      prefix = "";
    }
    if (postfix == null) {
      postfix = "";
    }
    Network nw = getNetwork();
    ChannelStats stats = getChannelStats(ch);

    String[] lines = message.split("\n");
    for (String line : lines) {
      String[] split = IRCUtility.breakUpMessageByIRCLineLength(channel, line);
      for (String l : split) {
        String raw = "PRIVMSG " + channel + " :" + prefix + l + postfix;
        this.outputQueue.addLine(raw);
        if (ch != null) {
          stats.addToLinesSent(1);
        }
        nw.addToLinesSent(1);
      }
    }
    if (stats != null && ch != null) {
      this.channelStatsService.save(stats);
    }
    this.networkService.save(nw);
  }


}


