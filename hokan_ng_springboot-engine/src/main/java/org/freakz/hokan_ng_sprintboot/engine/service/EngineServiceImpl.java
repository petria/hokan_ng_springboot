package org.freakz.hokan_ng_sprintboot.engine.service;

import java.util.List;

import org.freakz.hokan_ng_sprintboot.common.jms.JmsMessage;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.IrcServerConfig;
import org.freakz.hokan_ng_sprintboot.common.service.IrcServerConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * Created by petria on 10.2.2015.
 */
@Component
@Transactional
@Slf4j
public class EngineServiceImpl implements EngineService {

  @Autowired
  private IrcServerConfigService ircServerConfigService;


  @Override
  public void handleJmsMessage(JmsMessage jmsMessage) {
    List<IrcServerConfig> configs = ircServerConfigService.findAll();
    log.debug("Handling message: {}", configs.size());
  }

}
