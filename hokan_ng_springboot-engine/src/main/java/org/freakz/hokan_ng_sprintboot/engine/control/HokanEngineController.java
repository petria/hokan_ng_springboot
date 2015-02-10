package org.freakz.hokan_ng_sprintboot.engine.control;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_sprintboot.common.jms.JmsMessage;
import org.freakz.hokan_ng_sprintboot.common.service.IrcServerConfigService;
import org.freakz.hokan_ng_sprintboot.engine.control.api.EngineController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by petria on 10.2.2015.
 */
@Controller
@Slf4j
public class HokanEngineController implements EngineController {

  @Autowired
  private IrcServerConfigService ircServerConfigService;

  @Override
  public void handleJmsMessage(JmsMessage jmsMessage) {
    log.debug("Handling message..");
  }
}
