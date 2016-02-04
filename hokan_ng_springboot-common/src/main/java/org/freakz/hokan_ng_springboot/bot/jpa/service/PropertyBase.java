package org.freakz.hokan_ng_springboot.bot.jpa.service;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Petri Airio on 26.5.2015.
 *
 */
public abstract class PropertyBase {

  protected PropertyEntity findFirstByPropertyName(PropertyName propertyName) {
    throw new NotImplementedException();
  }

  protected ChannelPropertyEntity findFirstByChannelAndPropertyName(Channel channel, PropertyName propertyName) {
    throw new NotImplementedException();
  }

  public String getPropertyAsString(PropertyEntityBase property, String defaultValue) {
    if (property == null) {
      return defaultValue;
    }
    return property.getValue();
  }

  public String getPropertyAsString(PropertyName propertyName, String defaultValue) {
    return getPropertyAsString(findFirstByPropertyName(propertyName), defaultValue);
  }

  public String getChannelPropertyAsString(Channel channel, PropertyName propertyName, String defaultValue) {
    return getPropertyAsString(findFirstByChannelAndPropertyName(channel, propertyName), defaultValue);
  }


  // ----

  public int getPropertyAsInt(PropertyEntityBase property, int defaultValue) {
    if (property == null) {
      return defaultValue;
    }
    return Integer.parseInt(property.getValue());
  }

  public int getPropertyAsInt(PropertyName propertyName, int defaultValue) {
    return getPropertyAsInt(findFirstByPropertyName(propertyName), defaultValue);
  }

  public int getChannelPropertyAsInt(Channel channel, PropertyName propertyName, int defaultValue) {
    return getPropertyAsInt(findFirstByChannelAndPropertyName(channel, propertyName), defaultValue);
  }

  // ----

  public long getPropertyAsLong(PropertyEntityBase property, long defaultValue) {
    if (property == null) {
      return defaultValue;
    }
    return Long.parseLong(property.getValue());
  }

  public long getPropertyAsLong(PropertyName propertyName, long defaultValue) {
    return getPropertyAsLong(findFirstByPropertyName(propertyName), defaultValue);
  }

  public long getChannelPropertyAsLong(Channel channel, PropertyName propertyName, long defaultValue) {
    return getPropertyAsLong(findFirstByChannelAndPropertyName(channel, propertyName), defaultValue);
  }

  // ----

  public boolean getPropertyAsBoolean(PropertyEntityBase property, boolean defValue) {
    if (property == null) {
      return defValue;
    }
    String value = property.getValue();
    if (value == null) {
      return false;
    }
    if (value.toLowerCase().matches("1|true|on")) {
      return true;
    }
    ;
    return false;
  }

  public boolean getPropertyAsBoolean(PropertyName propertyName, boolean defValue) {
    return getPropertyAsBoolean(findFirstByPropertyName(propertyName), defValue);
  }

  public boolean getChannelPropertyAsBoolean(Channel channel, PropertyName propertyName, boolean value) {
    return getPropertyAsBoolean(findFirstByChannelAndPropertyName(channel, propertyName), value);
  }

}
