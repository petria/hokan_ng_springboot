package org.freakz.hokan_ng_sprintboot.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan({"org.freakz.hokan_ng_sprintboot.services", "org.freakz.hokan_ng_sprintboot.common.jms"})
//@EnableJpaRepositories({"org.freakz"})
@EnableAutoConfiguration
@EnableTransactionManagement

@Slf4j
public class HokanNgSpringBootServices {

  public static void main(String[] args) {
    SpringApplication.run(HokanNgSpringBootServices.class, args);
  }
}
