package org.freakz.hokan_ng_springboot.bot.jpa.entity;

import java.io.Serializable;

/**
 * Created by Petri Airio on 15.5.2015.
 *
 */
public enum CommandStatus implements Serializable {

  RUNNING,
  FINISHED,
  INTERRUPTED,
  ERROR

}
