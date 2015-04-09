package org.freakz.hokan_ng_springboot.bot.jms;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jms.api.JmsMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageListener;

/**
 *
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
    log.info("Setting Destination Name: {}", getDestinationName());
    container.setDestinationName(getDestinationName());
    container.setMessageListener((MessageListener) message -> {
      try {
//        log.debug("Got message: {} ... handling ", message);
        handleJmsMessage(message);
//        log.debug("... message handle done!");

      } catch (JMSException ex) {
        ex.printStackTrace();
      }
    });
    return container;
  }


}

