package org.freakz.hokan_ng_sprintboot.io;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableJms
@ComponentScan({ "org.freakz" })
@Slf4j
public class HokanNgSpringBootIo {

  public static void main(String[] args) {
    SpringApplication.run(HokanNgSpringBootIo.class, args);
  }

}
