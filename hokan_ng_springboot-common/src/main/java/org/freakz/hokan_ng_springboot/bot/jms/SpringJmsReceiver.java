package org.freakz.hokan_ng_springboot.bot.jms;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jms.api.JmsMessageHandler;
import org.freakz.hokan_ng_springboot.bot.service.UptimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 *
 * Created by petria on 6.2.2015.
 */
@Component
@Slf4j
public abstract class SpringJmsReceiver implements JmsMessageHandler {

  @Autowired
  private ConnectionFactory connectionFactory;

  @Autowired
  private UptimeService uptimeService;


  @Bean
  public ScheduledAnnotationBeanPostProcessor scheduledAnnotationBeanPostProcessor() {
    return new ScheduledAnnotationBeanPostProcessor();
  }

  @Bean
  public DefaultMessageListenerContainer messageListener() {
    DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
    container.setConnectionFactory(this.connectionFactory);
    log.info("Setting Destination Name: {}", getDestinationName());
    container.setDestinationName(getDestinationName());
    container.setMessageListener((MessageListener) message -> {
      try {
//        log.debug("Got message: {} ... handling ", message);
        ObjectMessage objectMessage = (ObjectMessage) message;
        JmsMessage jmsMessage = (JmsMessage) objectMessage.getObject();
        String command = (String) jmsMessage.getPayLoadObject("COMMAND");
        if (command.equals("PING")) {
          PingResponse pingResponse = new PingResponse();
          pingResponse.setUptime(uptimeService.getUptime());
          reply.addPayLoadObject("PING_RESPONSE", pingResponse);

        } else {
          handleJmsMessage(message);
        }

        Destination replyTo = message.getJMSReplyTo();
//      log.debug("got message: {}, replyTo: {}", jmsMessage, replyTo);
        if (replyTo != null) {
          if (jmsReplyMessage != null) {
            //log.info("Sending reply: {}", jmsReplyMessage);
            jmsSender.sendJmsMessage(replyTo, jmsReplyMessage);
          }
        }

//        log.debug("... message handle done!");

      } catch (JMSException ex) {
        ex.printStackTrace();
      }
    });
    return container;
  }


}

