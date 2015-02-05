package org.freakz.hokan_ng_sprintboot.io;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_sprintboot.io.jms.JmsReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class HokanNgSpringBootIo {

  @Autowired
  private JmsReceiver jmsReceiver;

  public static void main(String[] args) {
    SpringApplication.run(HokanNgSpringBootIo.class, args);
  }
}
