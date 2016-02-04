package org.freakz.hokan_ng_springboot.bot.service;

import org.freakz.hokan_ng_springboot.bot.enums.HokanModule;
import org.freakz.hokan_ng_springboot.bot.models.HokanStatusModel;

/**
 * Created by Petri Airio on 9.4.2015.
 *
 */
public interface HokanStatusService {

  HokanStatusModel getHokanStatus(HokanModule module);

  void setActivated(boolean activated);

}
