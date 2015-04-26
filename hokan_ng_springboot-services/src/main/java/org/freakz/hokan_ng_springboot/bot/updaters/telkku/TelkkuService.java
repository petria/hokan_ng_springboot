package org.freakz.hokan_ng_springboot.bot.updaters.telkku;

import org.freakz.hokan_ng_springboot.bot.models.TelkkuProgram;

import java.util.Date;
import java.util.List;

/**
 * User: petria
 * Date: 11/26/13
 * Time: 2:27 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
public interface TelkkuService {

  TelkkuProgram getCurrentProgram(Date time, String channel);

  TelkkuProgram getNextProgram(TelkkuProgram current, String channel);

  boolean isReady();

  String[] getChannels();

  List<TelkkuProgram> findPrograms(String program);

  TelkkuProgram findProgramById(int id);

  List<TelkkuProgram> findDailyPrograms(Date day);
}