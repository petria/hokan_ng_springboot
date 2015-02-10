package org.freakz.hokan_ng_sprintboot.common.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_sprintboot.common.exception.HokanDAOException;
import org.freakz.hokan_ng_sprintboot.common.exception.HokanException;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Channel;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.ChannelProperty;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Property;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.PropertyName;
import org.freakz.hokan_ng_sprintboot.common.util.StringStuff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * User: petria
 * Date: 12/4/13
 * Time: 9:36 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Component
@Slf4j
public class PropertiesImpl implements Properties {

  @Autowired
  private PropertyService service;

  private Property getProperty(PropertyName name, Object def) {
    try {
      Property property = service.findProperty(name);
      if (property == null) {
        property = new Property(name, "" + def, "");
      }
      return property;
    } catch (HokanException e) {
      log.error("property error", e);
    }
    return null;
  }

  private ChannelProperty getChannelProperty(Channel channel, PropertyName name, Object def) {
    ChannelProperty property = null;
    try {
      property = service.findChannelProperty(channel, name);
      if (property == null) {
        property = new ChannelProperty(channel, name, "" + def, "");
      }
      return property;
    } catch (Exception e) {
//      log.error("property error", e);
    }
    return property;

  }

  @Override
  public String getPropertyAsString(PropertyName name, String def) {
    Property property = getProperty(name, def);
    return property.getValue();
  }

  @Override
  public int getPropertyAsInt(PropertyName name, int def) {
    Property property = getProperty(name, def);
    return Integer.parseInt(property.getValue());
  }

  @Override
  public long getPropertyAsLong(PropertyName name, long def) {
    Property property = getProperty(name, def);
    return Long.parseLong(property.getValue());
  }

  @Override
  public boolean getPropertyAsBoolean(PropertyName name, boolean def) {
    Property property = getProperty(name, def);
    return Boolean.parseBoolean(property.getValue());
  }

  @Override
  public boolean getChannelPropertyAsBoolean(Channel channel, PropertyName name, boolean def) {
    ChannelProperty property = getChannelProperty(channel, name, def);
    return Boolean.parseBoolean(property.getValue());
  }

  //  @Override
  public ChannelProperty saveChannelProperty(ChannelProperty property) {
    try {
      return service.saveChannelProperty(property);
    } catch (HokanException e) {
      log.error("property error", e);
    }
    return null;
  }

  @Override
  public ChannelProperty setChannelProperty(Channel channel, PropertyName property, String value) {

    try {
      return service.setChannelProperty(channel, property, value);
    } catch (HokanDAOException e) {
      log.error("property error", e);
    }
    return null;
  }

  //  @Override
  public Property saveProperty(Property property) {
    try {
      return service.saveProperty(property);
    } catch (HokanException e) {
      log.error("property error", e);
    }
    return null;
  }

  @Override
  @SuppressWarnings("unchecked")
  public List getAllProperties() {
    List allProperties = new ArrayList<>();
    List<Property> props1 = service.getProperties();
    List<ChannelProperty> props2 = service.getChannelProperties();
    allProperties.addAll(props1);
    allProperties.addAll(props2);
    return allProperties;
  }

  @Override
  public List getChannelProperties(Channel channel) {
    return service.getChannelProperties(channel);
  }

  @Override
  public List<Channel> getChannelsWithProperty(PropertyName propertyName) {
    return service.getChannelsWithProperty(propertyName);
  }

  @Override
  public PropertyName getPropertyName(String property) {
    for (PropertyName prop : PropertyName.values()) {
      if (StringStuff.match(prop.toString(), property, true)) {
        return prop;
      }
    }
    return null;
  }
}
