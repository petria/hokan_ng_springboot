package org.freakz.hokan_ng_springboot.bot.jpa.repository.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.IrcServerConfig;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.IrcServerConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created on 22.2.2015.
 */
@Service
@Slf4j
public class RepositoryIrcServerConfigService implements IrcServerConfigService {

  @Autowired
  private IrcServerConfigRepository ircServerConfigRepository;

  @Override
  @Transactional(readOnly = true)
  public List<IrcServerConfig> getIrcServerConfigs() {
    return ircServerConfigRepository.findAll();
  }

  @Override
  @Transactional
  public void updateIrcServerConfig(IrcServerConfig configuredServer) {
    ircServerConfigRepository.save(configuredServer);
  }
}
