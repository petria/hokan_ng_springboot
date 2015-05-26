package org.freakz.hokan_ng_springboot.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.enums.HokanModule;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.PropertyEntity;
import org.freakz.hokan_ng_springboot.bot.jpa.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Petri Airio on 15.5.2015.
 *
 */
@Service
@Scope("singleton")
@Slf4j
public class HokanModuleServiceImpl implements HokanModuleService {

  @Autowired
  private PropertyService propertyService;

  private long sessionId;

  private HokanModule module;

  @Override
  public void setHokanModule(HokanModule module) {
    this.module = module;
    this.sessionId = new Date().getTime();
    log.info("Module set to {}", module.toString());
    PropertyEntity property = propertyService.findFirstByPropertyName(module.getModuleProperty());
    if (property == null) {
      property = new PropertyEntity(module.getModuleProperty(), "", "");
    }
    property.setValue(this.sessionId + "");
    propertyService.save(property);
  }

  @Override
  public HokanModule getHokanModule() {
    return this.module;
  }

  @Override
  public long getSessionId() {
    return this.sessionId;
  }

}
