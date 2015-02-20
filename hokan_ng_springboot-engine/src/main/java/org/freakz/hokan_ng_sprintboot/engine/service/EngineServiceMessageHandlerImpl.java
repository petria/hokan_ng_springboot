package org.freakz.hokan_ng_sprintboot.engine.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_sprintboot.common.jms.JmsMessage;
import org.freakz.hokan_ng_sprintboot.common.service.old.IrcServerConfigService_;
import org.freakz.hokan_ng_sprintboot.entity.IrcServerConfig;
import org.freakz.hokan_ng_sprintboot.service.JmsServiceMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * Created by petria on 10.2.2015.
 */
@Component
//@Transactional
@Slf4j
public class EngineServiceMessageHandlerImpl implements JmsServiceMessageHandler {

  @Autowired
  private IrcServerConfigService_ ircServerConfigService;

  @Override
  public JmsMessage handleJmsServiceMessage(JmsMessage jmsMessage) {
    log.debug("Handling message");
    List<IrcServerConfig> configs = ircServerConfigService.getIrcServerConfigs();
    JmsMessage reply = new JmsMessage();
    reply.addPayLoadObject("REPLY", "Configs size: " + configs.size());
    return reply;
  }
}
