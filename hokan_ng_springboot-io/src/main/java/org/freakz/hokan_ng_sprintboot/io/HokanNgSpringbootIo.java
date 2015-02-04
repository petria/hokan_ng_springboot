package org.freakz.hokan_ng_sprintboot.io;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_sprintboot.common.jmsmessages.JmsMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

import javax.jms.*;

@SpringBootApplication
@Slf4j
public class HokanNgSpringbootIo {

  @Autowired
  private ConnectionFactory connectionFactory;

  @Autowired
  private IOSender ioSender;

  @Bean
  public ScheduledAnnotationBeanPostProcessor scheduledAnnotationBeanPostProcessor() {
    return new ScheduledAnnotationBeanPostProcessor();
  }

  @Bean
  public DefaultMessageListenerContainer messageListener() {
    DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
    container.setConnectionFactory(this.connectionFactory);
    container.setDestinationName("HokanNGIoQueue");
    container.setMessageListener(new MessageListener() {
      @Override
      public void onMessage(Message message) {
        try {
          ObjectMessage objectMessage = (ObjectMessage) message;
          JmsMessage jmsMessage = (JmsMessage) objectMessage.getObject();
          Destination replyTo = message.getJMSReplyTo();
          log.debug("got message: {}, repplyTo: {}", jmsMessage, replyTo);
          if (replyTo != null) {
            ioSender.send(replyTo, "REPLY", "reply: " + jmsMessage.getPayLoadObject("TEXT"));
          }
        } catch (JMSException ex) {
          ex.printStackTrace();
        }
      }
    });
    return container;
  }


  public static void main(String[] args) {
    SpringApplication.run(HokanNgSpringbootIo.class, args);
  }
}
