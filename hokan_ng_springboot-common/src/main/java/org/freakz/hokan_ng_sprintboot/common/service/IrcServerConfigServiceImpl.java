package org.freakz.hokan_ng_sprintboot.common.service;

import java.util.List;

import org.freakz.hokan_ng_sprintboot.common.exception.HokanException;
import org.freakz.hokan_ng_sprintboot.common.jpa.dao.IrcServerConfigDAO;
import org.freakz.hokan_ng_sprintboot.common.jpa.dao.NetworkDAO;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.IrcServerConfig;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.IrcServerConfigState;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Network;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * Created by petria on 10.2.2015.
 */
@Component
@Slf4j
@Transactional
public class IrcServerConfigServiceImpl implements IrcServerConfigService {

  private final IrcServerConfigDAO ircServerConfigDAO;
  private final NetworkDAO networkDAO;

  @Autowired
  public IrcServerConfigServiceImpl(IrcServerConfigDAO ircServerConfigDAO, NetworkDAO networkDAO) {
    this.ircServerConfigDAO = ircServerConfigDAO;
    this.networkDAO = networkDAO;
  }

  @Override
  public List<IrcServerConfig> getIrcServerConfigs() {
    try {
      return ircServerConfigDAO.getIrcServerConfigs();
    } catch (HokanException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public IrcServerConfig createIrcServerConfig(String networkName, String server, int port, String password,
          boolean useThrottle, IrcServerConfigState state) throws HokanException {

    Network network = null;
    try {
      network = networkDAO.getNetwork(networkName);
    } catch (HokanException e) {
      // ignore
    }
    if (network == null) {
      network = networkDAO.createNetwork(networkName);
    }

    try {
      return ircServerConfigDAO.createIrcServerConfig(network, server, port, password, useThrottle, state);
    } catch (HokanException e) {
      throw new HokanException(e.getMessage());
    }
  }

  @Override
  public IrcServerConfig updateIrcServerConfig(IrcServerConfig ircServerConfig) {
    try {
      return ircServerConfigDAO.updateIrcServerConfig(ircServerConfig);
    } catch (HokanException e) {
      e.printStackTrace();
    }
    return null;
  }



}
