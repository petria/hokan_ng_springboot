package org.freakz.hokan_ng_springboot.bot.service;

import org.freakz.hokan_ng_springboot.bot.enums.HokanModule;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Created by Petri Airio on 15.5.2015.
 *
 */
@Service
@Scope("singleton")
public class HokanModuleServiceImpl implements HokanModuleService {

  private HokanModule module;

  @Override
  public void setHokanModule(HokanModule module) {
    this.module = module;
  }

  @Override
  public HokanModule getHokanModule() {
    return this.module;
  }

}
