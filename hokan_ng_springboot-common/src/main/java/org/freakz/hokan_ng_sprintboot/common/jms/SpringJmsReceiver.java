package org.freakz.hokan_ng_sprintboot.common.jms;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_sprintboot.common.jms.api.JmsMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageListener;

/**
 * Created by petria on 6.2.2015.
 */
@Component
@Slf4j
public abstract class SpringJmsReceiver implements JmsMessageHandler {

  @Autowired
  private ConnectionFactory connectionFactory;


  @Bean
  public ScheduledAnnotationBeanPostProcessor scheduledAnnotationBeanPostProcessor() {
    return new ScheduledAnnotationBeanPostProcessor();
  }

  @Bean
  public DefaultMessageListenerContainer messageListener() {
    DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
    container.setConnectionFactory(this.connectionFactory);
//    container.setDestinationName("HokanNGIoQueue");
    log.info("Setting Destination Name: {}", getDestinationName());
    container.setDestinationName(getDestinationName());
    container.setMessageListener((MessageListener) message -> {
      try {
        log.debug("Got message: {} ... handling ", message);
        handleJmsMessage(message);
        log.debug("... message handle done!");

/*        ObjectMessage objectMessage = (ObjectMessage) message;
        JmsMessage jmsMessage = (JmsMessage) objectMessage.getObject();
        Destination replyTo = message.getJMSReplyTo();
        log.debug("got message: {}, repplyTo: {}", jmsMessage, replyTo);
        if (replyTo != null) {
          jmsSender.send(replyTo, "REPLY", "reply: " + jmsMessage.getPayLoadObject("TEXT"));
        }*/
      } catch (JMSException ex) {
        ex.printStackTrace();
      }
    });
    return container;
  }


}

