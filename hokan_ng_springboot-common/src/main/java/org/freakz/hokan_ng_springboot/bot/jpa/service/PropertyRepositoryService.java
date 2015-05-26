package org.freakz.hokan_ng_springboot.bot.jpa.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.PropertyEntity;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.PropertyName;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Petri Airio on 27.3.2015.
 *
 */
@Service
@Slf4j
public class PropertyRepositoryService extends PropertyBase implements PropertyService {

  @Autowired
  private PropertyRepository repository;

  @Override
  public List<PropertyEntity> findAll() {
    return repository.findAll();
  }

  @Override
  public PropertyEntity save(PropertyEntity property) {
    return repository.save(property);
  }

  @Override
  public void delete(PropertyEntity object) {
    repository.delete(object);
  }

  @Override
  public PropertyEntity findFirstByPropertyName(PropertyName propertyName) {
    return repository.findFirstByPropertyName(propertyName);
  }

}
