package org.freakz.hokan_ng_springboot.bot.jms;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.events.IrcMessageEvent;
import org.freakz.hokan_ng_springboot.bot.jms.api.JmsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.ObjectMessage;

/**
 * Created by Petri Airio on 9.4.2015.
 */
@Service
@Slf4j
public class EngineCommunicatorImpl implements EngineCommunicator {

  @Autowired
  private JmsSender jmsSender;

  @Override
  public String sendToEngine(IrcMessageEvent event) {
    try {
      ObjectMessage reply = jmsSender.sendAndGetReply("HokanNGEngineQueue", "EVENT", event);
      if (reply != null) {
        JmsMessage jmsMessage = (JmsMessage) reply.getObject();
        log.info("reply: {}", jmsMessage);
        return "reply: " + jmsMessage.getPayLoadObject("REPLY");
      }
      return "No reply!";

    } catch (Exception e) {
      log.error("error", e);
    }
    return null;
  }
}
