package org.freakz.hokan_ng_springboot.bot.enums;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.PropertyName;

/**
 * Created by Petri Airio on 9.4.2015.
 *
 */
public enum HokanModule {

  HokanIo("HokanNGIoQueue", PropertyName.PROP_SYS_SESSION_ID_IO),
  HokanEngine("HokanNGEngineQueue", PropertyName.PROP_SYS_SESSION_ID_ENGINE),
  HokanServices("HokanNGServicesQueue", PropertyName.PROP_SYS_SESSION_ID_SERVICES),
  HokanUi("HokanNGUiQueue", PropertyName.PROP_SYS_SESSION_ID_UI);


  private final String queueName;
  private final PropertyName moduleProperty;

  HokanModule(String queueName, PropertyName moduleProperty) {
    this.queueName = queueName;
    this.moduleProperty = moduleProperty;
  }

  public String getQueueName() {
    return queueName;
  }

  public PropertyName getModuleProperty() {
    return moduleProperty;
  }

}
