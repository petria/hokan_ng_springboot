package org.freakz.hokan_ng_sprintboot.engine.control.api;

import org.freakz.hokan_ng_sprintboot.common.jms.JmsMessage;

/**
 * Created by petria on 10.2.2015.
 */
public interface EngineController {

  void handleJmsMessage(JmsMessage jmsMessage);

}
