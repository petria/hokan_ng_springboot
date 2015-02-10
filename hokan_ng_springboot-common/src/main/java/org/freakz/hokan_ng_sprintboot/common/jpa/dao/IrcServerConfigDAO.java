package org.freakz.hokan_ng_sprintboot.common.jpa.dao;


import org.freakz.hokan_ng_sprintboot.common.exception.HokanDAOException;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.IrcServerConfig;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.IrcServerConfigState;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Network;

import java.util.List;

/**
 * Date: 3.6.2013
 * Time: 11:35
 *
 * @author Petri Airio (petri.airio@siili.fi)
 */
public interface IrcServerConfigDAO {

  List<IrcServerConfig> getIrcServerConfigs() throws HokanDAOException;

  IrcServerConfig createIrcServerConfig(Network network, String server, int port, String password, boolean useThrottle, IrcServerConfigState state) throws HokanDAOException;

  IrcServerConfig updateIrcServerConfig(IrcServerConfig ircServerConfig) throws HokanDAOException;

}
