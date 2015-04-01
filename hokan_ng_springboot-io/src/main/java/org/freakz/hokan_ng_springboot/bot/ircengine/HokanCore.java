package org.freakz.hokan_ng_springboot.bot.ircengine;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.events.IrcEvent;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.ircengine.connector.EngineConnector;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.IrcServerConfig;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Network;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.User;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.service.ChannelService;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.service.JoinedUserService;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.service.NetworkService;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.service.UserService;
import org.freakz.hokan_ng_springboot.bot.util.StringStuff;
import org.jibble.pircbot.PircBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by AirioP on 17.2.2015.
 */
@Component
@Scope("prototype")
@Slf4j
public class HokanCore extends PircBot {

	@Autowired
	private ApplicationContext context;

	@Autowired
	private ChannelService channelService;

	@Autowired
	private JoinedUserService joinedUsersService;

	@Autowired
	private NetworkService networkService;

	@Autowired
	private UserService userService;


	private EngineConnector engineConnector;

	private IrcServerConfig ircServerConfig;
	private OutputQueue outputQueue;

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
		this.outputQueue = this.context.getBean(OutputQueue.class);
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
				user.setPassword("1234");
				user.setFullName(fullName);
				user = this.userService.save(user);
			}
/*			UserChannel userChannel = userChannelService.getUserChannel(user, channel);
			if (userChannel == null) {
				userChannelService.createUserChannel(user, channel);
			}
			this.joinedUsersService.createJoinedUser(channel, user, userModes);
		}
		TODO
		*/
		}
	}

}
