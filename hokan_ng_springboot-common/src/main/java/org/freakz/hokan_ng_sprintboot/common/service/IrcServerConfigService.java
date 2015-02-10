package org.freakz.hokan_ng_sprintboot.common.service;


import org.freakz.hokan_ng_sprintboot.common.exception.HokanException;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.IrcServerConfig;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.IrcServerConfigState;

import java.util.List;

/**
 * Date: 10/31/13
 * Time: 11:40 AM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
public interface IrcServerConfigService {

  List<IrcServerConfig> getIrcServerConfigs();

  IrcServerConfig createIrcServerConfig(String network, String server, int port, String password, boolean useThrottle, IrcServerConfigState disconnected) throws HokanException;

  IrcServerConfig updateIrcServerConfig(IrcServerConfig ircServerConfig);

}
