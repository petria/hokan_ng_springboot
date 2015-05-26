package org.freakz.hokan_ng_springboot.bot.jpa.repository;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.PropertyEntity;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.PropertyName;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Petri Airio on 27.3.2015.
 *
 */
public interface PropertyRepository extends JpaRepository<PropertyEntity, Long> {

  PropertyEntity findFirstByPropertyName(PropertyName propertyName);

}
