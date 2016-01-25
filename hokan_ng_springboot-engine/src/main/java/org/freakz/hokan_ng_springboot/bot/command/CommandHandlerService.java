package org.freakz.hokan_ng_springboot.bot.command;

import org.freakz.hokan_ng_springboot.bot.command.handlers.Cmd;

import java.util.List;

/**
 * User: petria
 * Date: 11/6/13
 * Time: 4:08 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
public interface CommandHandlerService {

  Cmd getCommandHandler(String line);

  List<Cmd> getCommandHandlers();

  List<Cmd> getCommandHandlersByName(String name);

}
