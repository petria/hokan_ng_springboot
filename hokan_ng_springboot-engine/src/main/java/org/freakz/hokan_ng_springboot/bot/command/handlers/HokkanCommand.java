package org.freakz.hokan_ng_springboot.bot.command.handlers;


import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;

/**
 * User: petria
 * Date: 11/7/13
 * Time: 2:49 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
public interface HokkanCommand {

  String getMatchPattern();

  String getName();

  void handleLine(InternalRequest request, EngineResponse response) throws Exception;


}
