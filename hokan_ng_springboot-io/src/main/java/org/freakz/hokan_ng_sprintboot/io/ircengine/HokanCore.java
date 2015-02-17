package org.freakz.hokan_ng_sprintboot.io.ircengine;

import org.freakz.hokan_ng_sprintboot.common.jpa.entity.IrcServerConfig;
import org.jibble.pircbot.PircBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by AirioP on 17.2.2015.
 */
@Component
@Scope("prototype")
@Slf4j
public class HokanCore extends PircBot {

	@Autowired private ApplicationContext context;

	private IrcServerConfig ircServerConfig;
	private OutputQueue outputQueue;

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
}
