package org.freakz.hokan_ng_springboot.bot.command.handlers;


import com.martiansoftware.jsap.*;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.cmdpool.CommandPool;
import org.freakz.hokan_ng_springboot.bot.cmdpool.CommandRunnable;
import org.freakz.hokan_ng_springboot.bot.enums.HokanModule;
import org.freakz.hokan_ng_springboot.bot.events.*;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.jms.JmsMessage;
import org.freakz.hokan_ng_springboot.bot.jms.api.JmsSender;
import org.freakz.hokan_ng_springboot.bot.jpa.service.ChannelService;
import org.freakz.hokan_ng_springboot.bot.jpa.service.JoinedUserService;
import org.freakz.hokan_ng_springboot.bot.jpa.service.NetworkService;
import org.freakz.hokan_ng_springboot.bot.service.AccessControlService;
import org.freakz.hokan_ng_springboot.bot.util.CommandArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import java.util.*;

/**
 * User: petria
 * Date: 11/6/13
 * Time: 4:02 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Slf4j
public abstract class Cmd implements HokkanCommand, CommandRunnable {

  @Autowired
  private JmsSender jmsSender;

  @Autowired
  protected ApplicationContext context;

  static protected Map<HelpGroup, List<Cmd>> helpGroups = new HashMap<>();

  protected JSAP jsap;
  protected boolean channelOpOnly;
  protected boolean loggedInOnly;
  protected boolean channelOnly;
  protected boolean privateOnly;
  protected boolean masterUserOnly;
  protected boolean toBotOnly;
  protected boolean isChannelOp;
  protected boolean isLoggedIn;
  protected boolean isPublic;
  protected boolean isPrivate;
  protected boolean isMasterUser;
  protected boolean isToBot;

  @Autowired
  protected AccessControlService accessControlService;

  @Autowired
  protected ChannelService channelService;

  @Autowired
  protected CommandPool commandPool;

  @Autowired
  protected JoinedUserService joinedUsersService;

  @Autowired
  protected NetworkService networkService;

/*  @Autowired
  protected SearchReplaceService searchReplaceService;

  @Autowired
  protected UrlLoggerService urlLoggerService;
*/

  public Cmd() {
    jsap = new JSAP();
    jsap.setHelp("Help not set!");
  }

  protected void registerParameter(Parameter parameter) {
    try {
      jsap.registerParameter(parameter);
    } catch (JSAPException e) {
      log.error("Error registering command parameter", e);
    }

  }


  public String getMatchPattern() {
    return String.format("!%s.*", getName().toLowerCase());
  }

  public void addToHelpGroup(HelpGroup helpGroup, Cmd cmd) {
    List<Cmd> cmds = this.helpGroups.get(helpGroup);
    if (cmds == null) {
      cmds = new ArrayList<>();
      this.helpGroups.put(helpGroup, cmds);
    }
    cmds.add(cmd);
  }

  public List<HelpGroup> getCmdHelpGroups(Cmd cmd) {
    List<HelpGroup> inGroups = new ArrayList<>();
    for (HelpGroup group : this.helpGroups.keySet()) {
      List<Cmd> cmds = this.helpGroups.get(group);
      if (cmds.contains(cmd)) {
        inGroups.add(group);
      }
    }
    return inGroups;
  }

  public List<Cmd> getOtherCmdsInGroup(HelpGroup group, Cmd cmd) {
    List<Cmd> otherCmds = new ArrayList<>();
    for (Cmd cmdInGroup : this.helpGroups.get(group)) {
      if (cmdInGroup != cmd) {
        otherCmds.add(cmdInGroup);
      }
    }
    return otherCmds;
  }

  public String getName() {
    String name = this.getClass().getSimpleName();
    if (name.endsWith("Cmd")) {
      name = name.replaceAll("Cmd", "");
    }
    return name;
  }

  protected String buildSeeAlso(Cmd cmd) {
    Comparator<Cmd> comparator = new Comparator<Cmd>() {
      @Override
      public int compare(Cmd cmd1, Cmd cmd2) {
        return cmd1.getName().compareTo(cmd2.getName());
      }
    };

    String seeAlsoGroups = "";
    for (HelpGroup group : getCmdHelpGroups(cmd)) {
      List<Cmd> groupCmds = getOtherCmdsInGroup(group, cmd);
      Collections.sort(groupCmds, comparator);
      if (groupCmds.size() > 0) {
        for (Cmd groupCmd : groupCmds) {
          if (groupCmd == this) {
            continue;
          }
          seeAlsoGroups += " " + groupCmd.getName();
        }
      }
    }
    String seeAlsoHelp = "";
    if (seeAlsoGroups.length() > 0) {
      seeAlsoHelp = "\nSee also:" + seeAlsoGroups;
    }
    return seeAlsoHelp;
  }

  public void handleLine(InternalRequest request, EngineResponse response) throws Exception {
    IrcMessageEvent ircEvent = request.getIrcEvent();
    CommandArgs args = new CommandArgs(ircEvent.getMessage());

    response.setCommandClass(this.getClass().toString());

    if (args.hasArgs() && args.getArgs().equals("?")) {
      response.setResponseMessage("Usage: " + getName() + " " + jsap.getUsage() + "\n" + "Help: " + jsap.getHelp() + buildSeeAlso(this));

    } else {

      boolean parseRes;
      JSAPResult results = null;
      IDMap map = jsap.getIDMap();
      Iterator iterator = map.idIterator();
      String argsLine = args.joinArgs(1);
      if (iterator.hasNext()) {
        results = jsap.parse(argsLine);
        parseRes = results.success();
      } else {
        parseRes = true;
      }

      if (!parseRes) {
        response.setResponseMessage("Invalid arguments, usage: " + getName() + " " + jsap.getUsage());
      } else {
        if (checkAccess(request, response)) {
          ArgsWrapper wrapper = new ArgsWrapper();
          wrapper.request = request;
          wrapper.response = response;
          wrapper.results = results;
          commandPool.startSyncRunnable(this, wrapper);
        }
      }
    }
  }

  @Override
  public void handleRun(long myPid, Object args) throws HokanException {
    ArgsWrapper wrapper = (ArgsWrapper) ((Object[]) args)[0];
    handleRequest(wrapper.request, wrapper.response, wrapper.results);
  }

  public abstract void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) throws HokanException;

  private boolean checkAccess(InternalRequest request, EngineResponse response) {

    IrcMessageEvent ircMessageEvent = request.getIrcEvent();
    isLoggedIn = accessControlService.isLoggedIn(request.getUser());
    isPublic = !ircMessageEvent.isPrivate();
    isPrivate = ircMessageEvent.isPrivate();
    isToBot = ircMessageEvent.isToMe();
    isMasterUser = accessControlService.isAdminUser(request.getUser());
    isChannelOp = false; // TODO accessControlService.isChannelOp(ircMessageEvent, request.getChannel());

    boolean ret = true;

    if (isLoggedInOnly() && !isLoggedIn && !isMasterUser) {
      response.setResponseMessage("LoggedIn only: " + getName());
      response.setReplyTo(ircMessageEvent.getSender());
      ret = false;
    }

    if (isToBotOnly() && !isToBot && !isMasterUser) {
      response.setResponseMessage("Can be used via message to Bot only: " + getName());
      response.setReplyTo(ircMessageEvent.getSender());
      ret = false;
    }

    if (isChannelOnly() && !isPublic && !isMasterUser) {
      response.setResponseMessage("Can be used via channel messages only: " + getName());
      response.setReplyTo(ircMessageEvent.getSender());
      ret = false;
    }

    if (isPrivateOnly() && isPublic && !isMasterUser) {
      response.setResponseMessage("Can be used via private messages only: " + getName());
      response.setReplyTo(ircMessageEvent.getSender());
      ret = false;
    }

    if (isChannelOpOnly() && !isChannelOp && !isMasterUser) {
      response.setResponseMessage("ChannelOp only: " + getName());
      response.setReplyTo(ircMessageEvent.getSender());
      ret = false;
    }

    if (isMasterUserOnly() && !isMasterUser) {
      response.setResponseMessage("Master user only: " + getName());
      response.setReplyTo(ircMessageEvent.getSender());
      ret = false;
    }
    return ret;
  }

  public boolean isChannelOpOnly() {
    return channelOpOnly;
  }

  public void setChannelOpOnly(boolean channelOpOnly) {
    this.channelOpOnly = channelOpOnly;
  }

  // ---------------------

  public boolean isLoggedInOnly() {
    return loggedInOnly;
  }

  public void setLoggedInOnly(boolean loggedInOnly) {
    this.loggedInOnly = loggedInOnly;
  }

  public boolean isChannelOnly() {
    return channelOnly;
  }

  public void setChannelOnly(boolean channelOnly) {
    this.channelOnly = channelOnly;
  }

  public boolean isPrivateOnly() {
    return privateOnly;
  }

  public void setPrivateOnly(boolean privateOnly) {
    this.privateOnly = privateOnly;
  }

  public boolean isMasterUserOnly() {
    return masterUserOnly;
  }

  public void setMasterUserOnly(boolean masterUserOnly) {
    this.masterUserOnly = masterUserOnly;
  }

  public boolean isToBotOnly() {
    return toBotOnly;
  }

  public void setToBotOnly(boolean toBotOnly) {
    this.toBotOnly = toBotOnly;
  }

  public String getHelp() {
    return this.jsap.getHelp();
  }

  public void setHelp(String helpText) {
    this.jsap.setHelp(helpText);
  }

  public static class ArgsWrapper {
    public InternalRequest request;
    public EngineResponse response;
    public JSAPResult results;
  }

  public ServiceResponse doServicesRequest(String serviceCommand, IrcMessageEvent ircEvent, String... parameters) {
    ServiceRequest request = new ServiceRequest(ServiceRequest.ServiceRequestType.METAR_REQUEST, ircEvent, parameters);
    ObjectMessage objectMessage = jmsSender.sendAndGetReply(HokanModule.HokanServices.getQueueName(), "SERVICE_REQUEST", request, false);
    try {
      JmsMessage jmsMessage = (JmsMessage) objectMessage.getObject();
      ServiceResponse serviceResponse = jmsMessage.getServiceResponse();
      return serviceResponse;
    } catch (JMSException e) {
      log.error("jms", e);
    }
    return new ServiceResponse();

  }

}
