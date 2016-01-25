package org.freakz.hokan_ng_springboot.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.enums.HokanModule;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.PropertyEntity;
import org.freakz.hokan_ng_springboot.bot.jpa.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * Created by Petri Airio on 15.5.2015.
 *
 */
@Component
@Scope("singleton")
@Slf4j
public class HokanModuleServiceImpl implements HokanModuleService {

  @Autowired
  private PropertyService propertyService;

  private long sessionId;

  @Value("${hokan.module}")
  private String hokanModule;

  private HokanModule module;

  @PostConstruct
  public void setHokanModule() {
    log.debug("hokanModule: {}", hokanModule);
    this.module = HokanModule.valueOf(hokanModule);
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
