package org.freakz.hokan_ng_springboot.bot.events;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Network;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.User;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.UserChannel;
import org.freakz.hokan_ng_springboot.bot.jpa.service.ChannelService;
import org.freakz.hokan_ng_springboot.bot.jpa.service.NetworkService;
import org.freakz.hokan_ng_springboot.bot.jpa.service.UserChannelService;
import org.freakz.hokan_ng_springboot.bot.jpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * User: petria
 * Date: 12/10/13
 * Time: 3:45 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Component
@Slf4j
@Scope("prototype")
public class InternalRequest implements Serializable {

  @Autowired
  private NetworkService networkService;

  @Autowired
  private ChannelService channelService;

  @Autowired
  private UserChannelService userChannelService;

  @Autowired
  private UserService userService;


  private Network network;
  private Channel channel;
  private User user;
  private UserChannel userChannel;

  private IrcMessageEvent ircMessageEvent;

  public InternalRequest() {
  }

  public void init(IrcMessageEvent ircMessageEvent) throws HokanException {
    this.ircMessageEvent = ircMessageEvent;
    this.network = networkService.getNetwork(ircMessageEvent.getNetwork());
    this.user = getUser(ircMessageEvent);
    if (!ircMessageEvent.isPrivate()) {
      this.channel = channelService.findByNetworkAndChannelName(network, ircMessageEvent.getChannel());
      this.userChannel = userChannelService.getUserChannel(this.user, this.channel);
    }
  }


  public IrcMessageEvent getIrcEvent() {
    return this.ircMessageEvent;
  }

  public Network getNetwork() {
    return network;
  }

  public Channel getChannel() {
    return channel;
  }

  public User getUser() {
    return user;
  }

  public void updateUser() {
    userService.save(user);
  }

  public UserChannel getUserChannel() {
    return userChannel;
  }

  public void updateUserChannel() {
    userChannelService.save(userChannel);
  }

  private User getUser(IrcEvent ircEvent) {
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
    return user;
  }

}
