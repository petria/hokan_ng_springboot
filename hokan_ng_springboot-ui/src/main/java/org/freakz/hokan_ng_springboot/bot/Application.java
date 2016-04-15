/*
 * Copyright 2015 The original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.freakz.hokan_ng_springboot.bot;

import com.vaadin.server.CustomizedSystemMessages;
import com.vaadin.server.SystemMessages;
import com.vaadin.server.SystemMessagesInfo;
import com.vaadin.server.SystemMessagesProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.freakz.hokan_ng_springboot.bot.enums.CommandLineArgs;
import org.freakz.hokan_ng_springboot.bot.util.CommandLineArgsParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.vaadin.spring.security.annotation.EnableVaadinManagedSecurity;
import org.vaadin.spring.security.config.AuthenticationManagerConfigurer;

import javax.jms.ConnectionFactory;
import java.util.Map;

/**
 * Main entry point into the demo application.
 *
 * @author Petter Holmström (petter@vaadin.com)
 */
@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class, scanBasePackages = "org.freakz.hokan_ng_springboot.bot")
@EnableVaadinManagedSecurity
@EnableJpaRepositories({"org.freakz.hokan_ng_springboot.bot"})
@Slf4j
public class Application {

  private static String JMS_BROKER_URL = "tcp://localhost:61616";

  @Bean
  public ConnectionFactory connectionFactory() {
    return new ActiveMQConnectionFactory(JMS_BROKER_URL);
  }

  public static void main(String[] args) {
    CommandLineArgsParser parser = new CommandLineArgsParser(args);
    Map<CommandLineArgs, String> parsed = parser.parseArgs();
    String url = parsed.get(CommandLineArgs.JMS_BROKER_URL);
    if (url != null) {
      JMS_BROKER_URL = url;
    }
    log.debug("JMS_BROKER_URL: {}", JMS_BROKER_URL);

    SpringApplication.run(Application.class, args);
  }

  /**
   * Provide custom system messages to make sure the application is reloaded when the session expires.
   */
  @Bean
  SystemMessagesProvider systemMessagesProvider() {
    return new SystemMessagesProvider() {
      @Override
      public SystemMessages getSystemMessages(SystemMessagesInfo systemMessagesInfo) {
        CustomizedSystemMessages systemMessages = new CustomizedSystemMessages();
        systemMessages.setSessionExpiredNotificationEnabled(false);
        return systemMessages;
      }
    };
  }

  /**
   * Configure the authentication manager.
   */
  @Configuration
  static class AuthenticationConfiguration implements AuthenticationManagerConfigurer {

    @Autowired
    private HokanAuthenticationProvider hokanAuthenticationProvider;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(this.hokanAuthenticationProvider);
      auth.authenticationProvider(this.hokanAuthenticationProvider);
    }
  }
}
