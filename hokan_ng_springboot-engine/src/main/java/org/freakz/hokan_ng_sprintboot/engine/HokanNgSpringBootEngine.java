package org.freakz.hokan_ng_sprintboot.engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("org.freakz.hokan_ng_sprintboot.common")
@EnableAutoConfiguration
@ComponentScan({"org.freakz.hokan_ng_sprintboot.common", "org.freakz.hokan_ng_sprintboot.engine"})
@EnableJpaRepositories("org.freakz.hokan_ng_sprintboot.common")

public class HokanNgSpringBootEngine {

  public static void main(String[] args) {
    SpringApplication.run(HokanNgSpringBootEngine.class, args);
  }

}
