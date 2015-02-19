package org.freakz.hokan_ng_sprintboot.services.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import org.freakz.hokan_ng_sprintboot.common.exception.HokanException;
import org.freakz.hokan_ng_sprintboot.common.jms.JmsMessage;
import org.freakz.hokan_ng_sprintboot.common.jms.SpringJmsReceiver;
import org.freakz.hokan_ng_sprintboot.common.jms.api.JmsSender;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.IrcServerConfig;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.IrcServerConfigState;
import org.freakz.hokan_ng_sprintboot.common.service.IrcServerConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * Created by petria on 5.2.2015.
 */
@Component
@Slf4j
public class ServicesJmsReceiver extends SpringJmsReceiver {

  @Autowired
  private JmsSender jmsSender;

  @Autowired private IrcServerConfigService ircServerConfigService;

  @Override
  public String getDestinationName() {
    return "HokanNGServicesQueue";
  }

  @Override
  public void handleJmsMessage(Message message) throws JMSException {
    ObjectMessage objectMessage = (ObjectMessage) message;
    JmsMessage jmsMessage = (JmsMessage) objectMessage.getObject();
    //    engineService.handleJmsMessage(jmsMessage);
    Destination replyTo = message.getJMSReplyTo();
    log.debug("got message: {}, replyTo: {}", jmsMessage, replyTo);
    test();
    if (replyTo != null) {
      jmsSender.send(replyTo, "REPLY", "reply: " + jmsMessage.getPayLoadObject("TEXT"));
    }

  }

  public void test() {
    try {
      IrcServerConfig config = ircServerConfigService
              .createIrcServerConfig("foo", "bar", 666, "342", true, IrcServerConfigState.CONNECTED);
      config.setServerPassword("1234567886576776");

      ircServerConfigService.updateIrcServerConfig(config);

    } catch (HokanException e) {
      e.printStackTrace();
    }

  }

}
