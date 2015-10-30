package org.freakz.hokan_ng_springboot.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 29.10.2015.
 * -
 */
@Service
@Slf4j
public class SystemScriptRunnerServiceImpl implements SystemScriptRunnerService {

  enum HOST_OS {
    BSD,
    LINUX,
    WINDOWS
  }




  @Override
  public void runScript(SystemScript systemScript) {
    HOST_OS hostOs = detectHostOs();
    switch (hostOs) {
      case BSD:
      case LINUX:
        break;
      case WINDOWS:
        break;
    }
  }

  private HOST_OS detectHostOs() {
    return null;
  }
}
