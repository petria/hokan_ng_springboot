package org.freakz.hokan_ng_springboot.bot.command;

import org.freakz.hokan_ng_springboot.bot.command.handlers.Cmd;
import org.freakz.hokan_ng_springboot.bot.command.handlers.HelpGroup;

import java.util.List;

/**
 * Created by Petri Airio on 15.9.2015.
 *
 */
public interface CommandGroupService {

  void addCommandToHelpGroup(Cmd cmd, HelpGroup helpGroup);

  List<HelpGroup> getCmdHelpGroups(Cmd cmd);

  List<Cmd> getOtherCmdsInGroup(HelpGroup group, Cmd cmd);

}
