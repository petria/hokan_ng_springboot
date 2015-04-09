package org.freakz.hokan_ng_springboot.bot.wicketservices;

import org.freakz.hokan_ng_springboot.bot.enums.HokanModule;

/**
 * Created by Petri Airio on 9.4.2015.
 */
public interface HokanStatusService {

  String getHokanStatus(HokanModule module);

}
