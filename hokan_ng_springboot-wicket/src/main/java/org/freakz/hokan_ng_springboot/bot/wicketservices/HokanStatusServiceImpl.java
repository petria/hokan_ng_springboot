package org.freakz.hokan_ng_springboot.bot.wicketservices;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.enums.HokanModule;
import org.springframework.stereotype.Service;

/**
 * Created by Petri Airio on 9.4.2015.
 */
@Service
@Slf4j
public class HokanStatusServiceImpl implements HokanStatusService {

  private String engineStatus = "<unknown>";
  private String ioStatus = "<unknown>";
  private String servicesStatus = "<unknown>";

  public HokanStatusServiceImpl() {
  }

  @Override
  public String getHokanStatus(HokanModule module) {
    switch (module) {
      case HokanEngine:
        return engineStatus;
      case HokanIo:
        return ioStatus;
      case HokanServices:
        return servicesStatus;
    }
    return "<unknown module>";
  }
}
