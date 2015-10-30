package org.freakz.hokan_ng_springboot.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.util.JarNixScriptExecutor;
import org.freakz.hokan_ng_springboot.bot.util.JarWindowsBatExecutor;
import org.springframework.stereotype.Service;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 29.10.2015.
 * -
 */
@Service
@Slf4j
public class SystemScriptRunnerServiceImpl implements SystemScriptRunnerService {

  enum HostOSEnum {
    BSD,
    LINUX,
    OSX,
    WINDOWS,
    UNKNOWN_OS
  }


  @Override
  public String[] runScript(SystemScript systemScript, String... args) {
    HostOSEnum hostOs = detectHostOs();
    switch (hostOs) {
      case BSD:
      case LINUX:
      case OSX:
      case UNKNOWN_OS:
        return runNixSystemScript(systemScript, args);
      case WINDOWS:
        return runWindowsSystemScript(systemScript, args);
    }
    return new String[]{"N/A"};
  }

  private String[] runWindowsSystemScript(SystemScript systemScript, String[] args) {
    JarWindowsBatExecutor executor = new JarWindowsBatExecutor(systemScript.getWindowsScript(), "windows-1252");
    return executor.executeJarWindowsBat(args);
  }

  private String[] runNixSystemScript(SystemScript systemScript, String[] args) {
    JarNixScriptExecutor executor = new JarNixScriptExecutor(systemScript.getNixScript(), "UTF-8");
    return executor.executeJarScript(args);
  }

  private HostOSEnum detectHostOs() {
    String OS = System.getProperty("os.name").toLowerCase();
    HostOSEnum hostOSEnum = null;
    log.debug("os.name: {}", OS);
    if (OS.contains("win")) {
      hostOSEnum = HostOSEnum.WINDOWS;
    } else if (OS.contains("freebsd")) {
      hostOSEnum = HostOSEnum.BSD;
    } else if (OS.contains("mac")) {
      hostOSEnum = HostOSEnum.OSX;
    } else if (OS.contains("linux")) {
      hostOSEnum = HostOSEnum.LINUX;
    } else {
      hostOSEnum = HostOSEnum.UNKNOWN_OS;
    }
    log.debug("Detected OS: {}", hostOSEnum.toString());
    return hostOSEnum;
  }

}
