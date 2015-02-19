package org.freakz.hokan_ng_sprintboot.io.service;

import org.freakz.hokan_ng_sprintboot.common.exception.HokanServiceException;
import org.freakz.hokan_ng_sprintboot.common.jms.JmsMessage;
import org.freakz.hokan_ng_sprintboot.common.service.ConnectionManagerService;
import org.freakz.hokan_ng_sprintboot.common.service.JmsServiceMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * Created by petria on 10.2.2015.
 */
@Controller
//@Transactional
@Slf4j
public class IoServiceMessageHandlerImpl implements JmsServiceMessageHandler {

	private ConnectionManagerService connectionManagerService;

	@Autowired
	public void setConnectionManagerService(ConnectionManagerService connectionManagerService) {
		this.connectionManagerService = connectionManagerService;
	}

	@Override
	public JmsMessage handleJmsServiceMessage(JmsMessage jmsMessage) {
		log.debug("Handling message");
		String command = (String) jmsMessage.getPayLoadObject("COMMAND");
		JmsMessage reply = new JmsMessage();
		if (command.equals("GO_ONLINE")) {
			try {
				connectionManagerService.connect("DEVNET");
				reply.addPayLoadObject("REPLY", "Connecting to: DEVNET");
			} catch (HokanServiceException e) {
				reply.addPayLoadObject("REPLY", e.getMessage());
				//        e.printStackTrace();
			}
		}
		return reply;
	}
}
