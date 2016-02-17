package org.freakz.hokan_ng_springboot.bot.cmdpool;

import org.freakz.hokan_ng_springboot.bot.exception.HokanException;

/**
 * User: petria
 * Date: 11/5/13
 * Time: 12:06 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
public interface CommandRunnable {

  void handleRun(long myPid, Object args) throws HokanException;

}
