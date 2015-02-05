package org.freakz.hokan_ng_sprintboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

import javax.jms.*;

@SpringBootApplication
public class HokanNgSpringBootServices {

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
    container.setDestinationName("HokanNGServicesQueue");
    container.setMessageListener(new MessageListener() {
      @Override
      public void onMessage(Message message) {
        try {
          Destination destination = message.getJMSReplyTo();
          System.out.println(message.getBody(Object.class));
        } catch (JMSException ex) {
          ex.printStackTrace();
        }
      }
    });
    return container;
  }

  public static void main(String[] args) {
    SpringApplication.run(HokanNgSpringBootServices.class, args);
  }
}
