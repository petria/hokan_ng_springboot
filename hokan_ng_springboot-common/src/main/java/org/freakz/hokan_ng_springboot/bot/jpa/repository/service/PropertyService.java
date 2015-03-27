package org.freakz.hokan_ng_springboot.bot.jpa.repository.service;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.Property;

import java.util.List;

/**
 * Created by Petri Airio on 27.3.2015.
 */
public interface PropertyService {

  List<Property> findAll();

  Property save(Property property);

  void delete(Property object);

}
