package org.freakz.hokan_ng_sprintboot.common.service;

import org.freakz.hokan_ng_sprintboot.common.exception.HokanException;
import org.freakz.hokan_ng_sprintboot.common.exception.HokanServiceException;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Channel;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.User;
import org.freakz.hokan_ng_sprintboot.common.rest.InternalRequest;
import org.freakz.hokan_ng_sprintboot.common.rest.IrcEvent;

import java.util.List;

/**
 * User: petria
 * Date: 11/18/13
 * Time: 8:51 AM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
public interface AccessControlService {

  List<User> getMasterUsers() throws HokanException;

  List<User> getChannelOps(Channel channel) throws HokanServiceException;

  boolean isChannelOp(IrcEvent ircEvent, Channel ch);

  boolean isMasterUser(IrcEvent ircEvent);

  User login(InternalRequest request, String string);

  boolean isUserLoggedIn(User user);

//  boolean isBotOp(IrcEvent ircEvent);

  boolean isOp(Channel channel, User user);

}
