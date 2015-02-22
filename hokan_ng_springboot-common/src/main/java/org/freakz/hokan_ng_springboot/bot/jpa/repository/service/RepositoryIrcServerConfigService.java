package org.freakz.hokan_ng_springboot.bot.jpa.repository.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.IrcServerConfig;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.IrcServerConfigRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created on 22.2.2015.
 */
@Service
@Slf4j
public class RepositoryIrcServerConfigService implements IrcServerConfigService {

  @Resource
  private IrcServerConfigRepository ircServerConfigRepository;

  @Override
  public List<IrcServerConfig> getIrcServerConfigs() {
    return ircServerConfigRepository.findAll();
  }

  @Override
  public void updateIrcServerConfig(IrcServerConfig configuredServer) {
    ircServerConfigRepository.save(configuredServer);
  }
}
