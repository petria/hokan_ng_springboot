package org.freakz.hokan_ng_sprintboot.io.jms;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_sprintboot.common.jms.JmsMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 *
 * Created by petria on 5.2.2015.
 */
@Component
@Slf4j
public class JmsReceiver {

  @Autowired
  private ConnectionFactory connectionFactory;

  @Autowired
  private JmsSender jmsSender;

  @Bean
  public ScheduledAnnotationBeanPostProcessor scheduledAnnotationBeanPostProcessor() {
    return new ScheduledAnnotationBeanPostProcessor();
  }

  @Bean
  public DefaultMessageListenerContainer messageListener() {
    DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
    container.setConnectionFactory(this.connectionFactory);
    container.setDestinationName("HokanNGIoQueue");
    container.setMessageListener((MessageListener) message -> {
      try {
        ObjectMessage objectMessage = (ObjectMessage) message;
        JmsMessage jmsMessage = (JmsMessage) objectMessage.getObject();
        Destination replyTo = message.getJMSReplyTo();
        log.debug("got message: {}, repplyTo: {}", jmsMessage, replyTo);
        if (replyTo != null) {
          jmsSender.send(replyTo, "REPLY", "reply: " + jmsMessage.getPayLoadObject("TEXT"));
        }
      } catch (JMSException ex) {
        ex.printStackTrace();
      }
    });
    return container;
  }

}
