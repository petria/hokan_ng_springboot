package org.freakz.hokan_ng_springboot.bot;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.jms.ConnectionFactory;

@Configuration
@ComponentScan({"org.freakz.hokan_ng_springboot.bot"})
@EnableJpaRepositories({"org.freakz.hokan_ng_springboot.bot"})
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
@Slf4j
public class HokanNgSpringBootEngine {

  private static String JMS_BROKER_URL = "tcp://localhost:61616";

  @Bean
  public ConnectionFactory connectionFactory() {
    return new ActiveMQConnectionFactory(JMS_BROKER_URL);
  }

  public static void main(String[] args) {

    if (args.length == 1) {
      JMS_BROKER_URL = args[0];
    }
    log.debug("JMS_BROKER_URL: {}", JMS_BROKER_URL);

    SpringApplication.run(HokanNgSpringBootEngine.class, args);
  }

}
