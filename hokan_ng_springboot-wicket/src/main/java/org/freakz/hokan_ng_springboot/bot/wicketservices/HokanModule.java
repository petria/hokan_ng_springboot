package org.freakz.hokan_ng_springboot.bot.wicketservices;

import org.freakz.hokan_ng_springboot.bot.service.HokanModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by Petri Airio on 15.5.2015.
 *
 */
@Service
public class HokanModule {

  @Autowired
  private HokanModuleService moduleService;

  @PostConstruct
  private void setModule() {
    moduleService.setHokanModule(org.freakz.hokan_ng_springboot.bot.enums.HokanModule.HokanWicket);
  }

}
