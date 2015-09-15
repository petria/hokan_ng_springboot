package org.freakz.hokan_ng_springboot.bot.command;

import org.freakz.hokan_ng_springboot.bot.command.handlers.Cmd;
import org.freakz.hokan_ng_springboot.bot.command.handlers.HelpGroup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Petri Airio on 15.9.2015.
 *
 */
@Service
@Scope("singleton")
public class CommandGroupServiceImpl implements CommandGroupService {

  protected Map<HelpGroup, List<Cmd>> helpGroups = new HashMap<>();

  @Override
  public void addCommandToHelpGroup(Cmd cmd, HelpGroup helpGroup) {
    List<Cmd> cmds = helpGroups.get(helpGroup);
    if (cmds == null) {
      cmds = new ArrayList<>();
      helpGroups.put(helpGroup, cmds);
    }
    cmds.add(cmd);

  }

  @Override
  public List<HelpGroup> getCmdHelpGroups(Cmd cmd) {
    List<HelpGroup> inGroups = new ArrayList<>();
    for (HelpGroup group : helpGroups.keySet()) {
      List<Cmd> cmds = helpGroups.get(group);
      if (cmds.contains(cmd)) {
        inGroups.add(group);
      }
    }
    return inGroups;
  }

  @Override
  public List<Cmd> getOtherCmdsInGroup(HelpGroup group, Cmd cmd) {
    List<Cmd> otherCmds = new ArrayList<>();
    for (Cmd cmdInGroup : helpGroups.get(group)) {
      if (cmdInGroup != cmd) {
        otherCmds.add(cmdInGroup);
      }
    }
    return otherCmds;
  }

}
