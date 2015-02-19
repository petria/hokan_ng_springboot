package org.freakz.hokan_ng_sprintboot.io.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * User: petria
 * Date: 2/19/12
 * Time: 4:35 PM
 */
@Entity
@Table(name = "PROPERTIES")
public class Property extends PropertyBase implements Serializable {

  public Property() {
  }

  public Property(PropertyName property, String value, String flags) {
    super(property, value, flags);
  }

  public String toString() {
    return String.format("%s = %s", getProperty().toString(), getValue());
  }

}
