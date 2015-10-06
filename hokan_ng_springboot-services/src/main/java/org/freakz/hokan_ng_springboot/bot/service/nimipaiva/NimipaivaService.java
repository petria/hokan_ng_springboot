package org.freakz.hokan_ng_springboot.bot.service.nimipaiva;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by Petri Airio on 5.10.2015.
 *
 */
public interface NimipaivaService {

  List<String> getNamesForDay(DateTime day);

  DateTime findDayForName(String name);

  void loadNames();

}
