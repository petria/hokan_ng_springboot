package org.freakz.hokan_ng_springboot.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan({"org.freakz.hokan_ng_springboot.bot"})
@EnableJpaRepositories({"org.freakz.hokan_ng_springboot.bot"})

//
@EnableAutoConfiguration
@EnableTransactionManagement

@Slf4j
public class HokanNgSpringBootIo {

  public static void main(String[] args) {
    SpringApplication.run(HokanNgSpringBootIo.class, args);
  }

}
