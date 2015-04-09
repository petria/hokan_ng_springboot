package org.freakz.hokan_ng_springboot.bot.command;

import org.freakz.hokan_ng_springboot.bot.command.handlers.Cmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: petria
 * Date: 11/6/13
 * Time: 4:00 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Service
public class CommandHandlerServiceImpl implements CommandHandlerService {

  @Autowired
  private ApplicationContext context;

  private Map<String, Cmd> handlers;

  public CommandHandlerServiceImpl() {
  }

  @PostConstruct
  public void refreshHandlers() {
    handlers = context.getBeansOfType(Cmd.class);
  }

  @Override
  public Cmd getCommandHandler(String line) {
    List<Cmd> matches = new ArrayList<>();
    for (Cmd base : this.handlers.values()) {
      if (line.matches(base.getMatchPattern())) {
        matches.add(base);
      }
    }
    Cmd theCmd = null;
    if (matches.size() == 1) {
      theCmd = matches.get(0);
    } else if (matches.size() > 1) {
      String firstWord = line.split(" ")[0];
      for (Cmd base : matches) {
        if (base.getMatchPattern().startsWith(firstWord)) {
          theCmd = base;
        }
      }
    }
    if (theCmd != null) {
      return context.getBean(theCmd.getClass());
    }
    return null;
  }

  @Override
  public List<Cmd> getCommandHandlers() {
    return new ArrayList<>(this.handlers.values());
  }

  @Override
  public List<Cmd> getCommandHandlersByName(String name) {
    List<Cmd> matches = new ArrayList<>();
    for (Cmd cmd : getCommandHandlers()) {
      if (cmd.getName().toLowerCase().startsWith(name.toLowerCase())) {
        matches.add(cmd);
      }
    }
    return matches;
  }
}
