package org.freakz.hokan_ng_springboot.bot.command.handlers;


import com.martiansoftware.jsap.*;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.cmdpool.CommandPool;
import org.freakz.hokan_ng_springboot.bot.cmdpool.CommandRunnable;
import org.freakz.hokan_ng_springboot.bot.command.HelpGroup;
import org.freakz.hokan_ng_springboot.bot.command.annotation.HelpGroups;
import org.freakz.hokan_ng_springboot.bot.enums.HokanModule;
import org.freakz.hokan_ng_springboot.bot.events.*;
import org.freakz.hokan_ng_springboot.bot.exception.HokanEngineException;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.jms.JmsMessage;
import org.freakz.hokan_ng_springboot.bot.jms.api.JmsSender;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.service.*;
import org.freakz.hokan_ng_springboot.bot.service.AccessControlService;
import org.freakz.hokan_ng_springboot.bot.service.HokanStatusService;
import org.freakz.hokan_ng_springboot.bot.service.StatsService;
import org.freakz.hokan_ng_springboot.bot.service.SystemScriptRunnerService;
import org.freakz.hokan_ng_springboot.bot.util.CommandArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import java.lang.annotation.Annotation;
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


  protected JSAP jsap;
  protected boolean channelOpOnly;
  protected boolean loggedInOnly;
  protected boolean channelOnly;
  protected boolean privateOnly;
  protected boolean adminUserOnly;
  protected boolean toBotOnly;

  protected boolean isChannelOp;
  protected boolean isLoggedIn;
  protected boolean isPublic;
  protected boolean isPrivate;
  protected boolean isAdminUser;
  protected boolean isToBot;

  @Autowired
  protected AccessControlService accessControlService;

  @Autowired
  protected AliasService aliasService;

  @Autowired
  protected ChannelService channelService;

  @Autowired
  protected ChannelStatsService channelStatsService;

  @Autowired
  protected ChannelPropertyService channelPropertyService;

  @Autowired
  protected CommandHistoryService commandHistoryService;

  @Autowired
  protected CommandPool commandPool;

  @Autowired
  protected IrcLogService ircLogService;

  @Autowired
  protected JoinedUserService joinedUsersService;

  @Autowired
  protected NetworkService networkService;

  @Autowired
  protected PropertyService propertyService;

  @Autowired
  protected SystemScriptRunnerService scriptRunnerService;

  @Autowired
  protected SearchReplaceService searchReplaceService;

  @Autowired
  protected  HokanStatusService statusService;

  @Autowired
  protected StatsService statsService;

  @Autowired
  protected UrlLoggerService urlLoggerService;

  @Autowired
  protected UserService userService;

  @Autowired
  protected UserChannelService userChannelService;

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


  public String getName() {
    String name = this.getClass().getSimpleName();
    if (name.endsWith("Cmd")) {
      name = name.replaceAll("Cmd", "");
    }
    return name;
  }

  protected Channel getRequiredChannel(String channelId) {
    long id;
    try {
      id = Long.parseLong(channelId);
    } catch (NumberFormatException ex) {
      return null;
    }
/*    theChannel = channelService.findOne(id);
    if (theChannel == null) {
      response.addResponse("No valid Channel found with id: %d, try: !chanlist to get ID.", id);
      return null;
    }
    TODO: fix
    */

    return null;
  }

  protected String getChannelIdOrFail(String channelId, InternalRequest request, EngineResponse response) {
    if (request.getIrcEvent().isPrivate() && channelId == null) {
      response.addResponse("ChannelID parameter is needed when using private message, try: !chanlist to get ID.");
      return null;
    }
    channelId = "<current>";
    return channelId;
  }

  protected Channel getChannelOrFail(String channelId, InternalRequest request, EngineResponse response) {
    if (channelId.equals("<current>")) {
      return request.getChannel();
    }
    Channel theChannel = request.getChannel();

    long id;
    try {
      id = Long.parseLong(channelId);
    } catch (NumberFormatException ex) {
      response.addResponse("Valid ChannelID parameter is needed, try: !chanlist");
      return null;
    }
    theChannel = channelService.findOne(id);
    if (theChannel == null) {
      response.addResponse("No valid Channel found with id: %d, try: !chanlist to get ID.", id);
      return null;
    }

    return theChannel;
  }


  protected String buildSeeAlso(Cmd cmd) {

    Comparator<Cmd> comparator = (cmd1, cmd2) -> cmd1.getName().compareTo(cmd2.getName());

    String seeAlsoGroups = "";
    for (HelpGroup group : getCmdHelpGroups(cmd)) {
      List<Cmd> groupCmds = getOtherCmdsInGroup(group);
      Collections.sort(groupCmds, comparator);
      if (groupCmds.size() > 0) {
        for (Cmd groupCmd : groupCmds) {
          if (groupCmd.getName().equals(cmd.getName())) {
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

  protected List<Cmd> getOtherCmdsInGroup(HelpGroup group) {
    List<Cmd> other = new ArrayList<>();
    for (Cmd theCmd : context.getBeansOfType(Cmd.class).values()) {
      Class obj = theCmd.getClass();
      if (obj.isAnnotationPresent(HelpGroups.class)) {
        Annotation annotation = obj.getAnnotation(HelpGroups.class);
        HelpGroups helpGroups = (HelpGroups) annotation;
        HelpGroup[] groups = helpGroups.helpGroups();
        for (HelpGroup cmdGroup : groups) {
          if (cmdGroup == group) {
            other.add(theCmd);
          }
        }
      }
    }
    return other;
  }

  protected HelpGroup[] getCmdHelpGroups(Cmd cmd) {
    Class obj = cmd.getClass();
    if (obj.isAnnotationPresent(HelpGroups.class)) {
      Annotation annotation = obj.getAnnotation(HelpGroups.class);
      HelpGroups helpGroups = (HelpGroups) annotation;
      HelpGroup[] groups = helpGroups.helpGroups();
      return groups;
    }
    return new HelpGroup[0];
  }

  public void handleLine(InternalRequest request, EngineResponse response) {
    IrcMessageEvent ircEvent = request.getIrcEvent();
    CommandArgs args = new CommandArgs(ircEvent.getMessage());

    response.setCommandClass(this.getClass().toString());

    if (args.hasArgs() && args.getArgs().equals("?")) {
      response.setResponseMessage("Usage: " + getName() + " " + jsap.getUsage() + "\n" + "Help: " + jsap.getHelp() + buildSeeAlso(this));
      sendReply(response);
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
        sendReply(response);
      } else {
        if (checkAccess(request, response)) {
          ArgsWrapper wrapper = new ArgsWrapper();
          wrapper.request = request;
          wrapper.response = response;
          wrapper.results = results;
          commandPool.startRunnable(this, request.getUser().getNick(), wrapper);
        } else {
          sendReply(response);
        }
      }
    }
  }

  @Override
  public void handleRun(long myPid, Object args) throws HokanException {
    ArgsWrapper wrapper = (ArgsWrapper) args;
    try {
      handleRequest(wrapper.request, wrapper.response, wrapper.results);
    } catch (Exception e) {
      HokanEngineException engineException = new HokanEngineException(e, getClass().getName());
      wrapper.response.setException(engineException);
      log.error("Command handler returned exception {}", e);
    }
    sendReply(wrapper.response);
  }

  private void sendReply(EngineResponse response) {
//    log.debug("Sending response: {}", response);
    jmsSender.send(HokanModule.HokanIo.getQueueName(), "ENGINE_RESPONSE", response, false);
  }


  public abstract void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) throws HokanException;

  private boolean checkAccess(InternalRequest request, EngineResponse response) {

    IrcMessageEvent ircMessageEvent = request.getIrcEvent();
    isLoggedIn = accessControlService.isLoggedIn(request.getUser());
    isPublic = !ircMessageEvent.isPrivate();
    isPrivate = ircMessageEvent.isPrivate();
    isToBot = ircMessageEvent.isToMe();
    isAdminUser = accessControlService.isAdminUser(request.getUser());
    isChannelOp = accessControlService.isChannelOp(request.getUser(), request.getChannel());

    boolean ret = true;

    if (isLoggedInOnly() && !isLoggedIn && !isAdminUser) {
      response.setResponseMessage("LoggedIn only: " + getName());
      response.setReplyTo(ircMessageEvent.getSender());
      ret = false;
    }

    if (isToBotOnly() && !isToBot && !isAdminUser) {
      response.setResponseMessage("Can be used via message to Bot only: " + getName());
      response.setReplyTo(ircMessageEvent.getSender());
      ret = false;
    }

    if (isChannelOnly() && !isPublic && !isAdminUser) {
      response.setResponseMessage("Can be used via channel messages only: " + getName());
      response.setReplyTo(ircMessageEvent.getSender());
      ret = false;
    }

    if (isPrivateOnly() && isPublic && !isAdminUser) {
      response.setResponseMessage("Can be used via private messages only: " + getName());
      response.setReplyTo(ircMessageEvent.getSender());
      ret = false;
    }

    if (isChannelOpOnly() && !isChannelOp && !isAdminUser) {
      response.setResponseMessage("ChannelOp only: " + getName());
      response.setReplyTo(ircMessageEvent.getSender());
      ret = false;
    }

    if (isAdminUserOnly() && !isAdminUser) {
      response.setResponseMessage("Admin user only: " + getName());
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

  public boolean isAdminUserOnly() {
    return this.adminUserOnly;
  }

  public void setAdminUserOnly(boolean adminUserOnly) {
    this.adminUserOnly = adminUserOnly;
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

  public ServiceResponse doServicesRequest(ServiceRequestType requestType, IrcMessageEvent ircEvent, Object... parameters) throws HokanException {
    ServiceRequest request = new ServiceRequest(requestType, ircEvent, new CommandArgs(ircEvent.getMessage()), parameters);
    ObjectMessage objectMessage = jmsSender.sendAndGetReply(HokanModule.HokanServices.getQueueName(), "SERVICE_REQUEST", request, false);
    if (objectMessage == null) {
      throw new HokanException("ServiceResponse null, is Services module running?");
    }
    try {
      JmsMessage jmsMessage = (JmsMessage) objectMessage.getObject();
      return jmsMessage.getServiceResponse();
    } catch (JMSException e) {
      log.error("jms", e);
    }
    return new ServiceResponse(requestType);

  }

}
