package org.freakz.hokan_ng_sprintboot.common.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_sprintboot.common.exception.HokanDAOException;
import org.freakz.hokan_ng_sprintboot.common.jpa.dao.IrcServerConfigDAO;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.IrcServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * Created by petria on 10.2.2015.
 */
@Component
@Slf4j
@Transactional
public class IrcServerConfigServiceImpl implements IrcServerConfigService {


  private final IrcServerConfigDAO ircServerConfigDAO;

  @Autowired
  public IrcServerConfigServiceImpl(IrcServerConfigDAO ircServerConfigDAO) {
    this.ircServerConfigDAO = ircServerConfigDAO;
  }


  @Override
  public Iterable<IrcServerConfig> findAll() {
    log.debug("Finding all...");
    try {
      List<IrcServerConfig> configs = ircServerConfigDAO.getIrcServerConfigs();
    } catch (HokanDAOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
