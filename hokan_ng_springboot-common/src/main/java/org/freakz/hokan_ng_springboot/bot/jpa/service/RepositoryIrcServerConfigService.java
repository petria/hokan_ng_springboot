package org.freakz.hokan_ng_springboot.bot.jpa.service;

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
  private IrcServerConfigRepository repository;

  @Override
  @Transactional(readOnly = true)
  public List<IrcServerConfig> findAll() {
    return repository.findAll();
  }

  @Override
  @Transactional
  public IrcServerConfig save(IrcServerConfig configuredServer) {
    return repository.save(configuredServer);
  }

  @Override
  public void delete(IrcServerConfig object) {
    repository.delete(object);
  }

}
