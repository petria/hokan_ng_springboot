package org.freakz.hokan_ng_springboot.bot.jms;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.service.ConnectionManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by petria on 5.2.2015.
 *
 */
@Component
@Slf4j
public class IoJmsReceiver extends SpringJmsReceiver {

  @Autowired
  private ConnectionManagerService connectionManagerService;

  @Override
  public String getDestinationName() {
    return "HokanNGIoQueue";
  }

  @Override
  public void handleJmsEnvelope(JmsEnvelope envelope) throws Exception {
    if (envelope.getMessageIn().getPayLoadObject("ENGINE_RESPONSE") != null) {
      handleEngineReply(envelope);
    } else {
      // handle others
    }

  }

  private void handleEngineReply(JmsEnvelope envelope) {
    EngineResponse response = (EngineResponse) envelope.getMessageIn().getPayLoadObject("ENGINE_RESPONSE");
    log.debug("handling engine response: {}", response);
    connectionManagerService.handleEngineResponse(response);
  }


/*  @Override
  public void handleJmsMessage(Message message) throws JMSException {
    ObjectMessage objectMessage = (ObjectMessage) message;
    JmsMessage jmsMessage = (JmsMessage) objectMessage.getObject();
    if (jmsMessage.getPayLoadObject("ENGINE_RESPONSE") != null) {
      handleEngineReply(jmsMessage);
    } else {

      try {
//        log.info("---->");
        JmsMessage jmsReplyMessage = jmsServiceMessageHandler.handleJmsServiceMessage(jmsMessage);
//        log.info("<----");
      } catch (Exception e) {
        jmsReplyMessage = new JmsMessage();
        jmsReplyMessage.addPayLoadObject("REPLY", e.getMessage());
        e.printStackTrace();
        log.error("Something went wrong!");
      }
      Destination replyTo = message.getJMSReplyTo();
//      log.debug("got message: {}, replyTo: {}", jmsMessage, replyTo);
      if (replyTo != null) {
        if (jmsReplyMessage != null) {
          //log.info("Sending reply: {}", jmsReplyMessage);
          jmsSender.sendJmsMessage(replyTo, jmsReplyMessage);
        }
      }
    }

  }*/

}
