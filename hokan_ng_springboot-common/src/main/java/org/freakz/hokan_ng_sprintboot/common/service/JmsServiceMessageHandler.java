package org.freakz.hokan_ng_sprintboot.common.service;

import org.freakz.hokan_ng_sprintboot.common.jms.JmsMessage;

/**
 * Created by petria on 10.2.2015.
 */
public interface JmsServiceMessageHandler {

	JmsMessage handleJmsServiceMessage(JmsMessage jmsMessage);

}
