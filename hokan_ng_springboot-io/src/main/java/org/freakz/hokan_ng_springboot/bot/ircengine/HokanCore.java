package org.freakz.hokan_ng_springboot.bot.ircengine;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.events.IrcEvent;
import org.freakz.hokan_ng_springboot.bot.events.IrcEventFactory;
import org.freakz.hokan_ng_springboot.bot.events.IrcMessageEvent;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.ircengine.connector.EngineConnector;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.*;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.service.*;
import org.freakz.hokan_ng_springboot.bot.util.StringStuff;
import org.jibble.pircbot.PircBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by AirioP on 17.2.2015.
 *
 */
@Component
@Scope("prototype")
@Slf4j
public class HokanCore extends PircBot {

  @Autowired
  private ChannelService channelService;

  @Autowired
  private ChannelStatsService channelStatsService;

  @Autowired
  private JoinedUserService joinedUsersService;

  @Autowired
  private NetworkService networkService;

  @Autowired
  private UserChannelService userChannelService;

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
  protected void onMessage(String channel, String sender, String login, String hostname, String message) {
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

/*    boolean wlt = properties.getChannelPropertyAsBoolean(ch, PropertyName.PROP_CHANNEL_DO_WHOLELINE_TRICKERS, false);
    if (wlt || ircEvent.isToMe()) {
      WholeLineTrickers wholeLineTrickers = new WholeLineTrickers(this);
      wholeLineTrickers.checkWholeLineTrickers(ircEvent);
    }
    urlLoggerService.catchUrls(ircEvent, ch, this);

    if (accessControlService.isMasterUser(ircEvent)) {
      handleBuiltInCommands(ircEvent);
    }
*/
    log.info(">>> TODO handle: {}", ircEvent);
/* TODO
    EngineRequest request = new EngineRequest(ircEvent);
    this.engineCommunicator.sendEngineMessage(request, this);
*/


    this.channelService.save(ch);
  }


}


