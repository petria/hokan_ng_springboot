package org.freakz.hokan_ng_springboot.bot.jpa.service;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.Property;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.PropertyName;

import java.util.List;

/**
 * Created by Petri Airio on 27.3.2015.
 *
 */
public interface PropertyService {

  List<Property> findAll();

  Property save(Property property);

  void delete(Property object);

  Property findFirstByPropertyName(PropertyName propertyName);

  String getPropertyAsString(PropertyName propertyName, String defaultValue);

  int getPropertyAsInt(PropertyName propertyName, int defaultValue);

  long getPropertyAsLong(PropertyName propertyName, long defaultValue);

  boolean getPropertyAsBoolean(PropertyName propertyName);

}
