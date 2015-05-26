package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.UnflaggedOption;
import org.freakz.hokan_ng_springboot.bot.command.CommandHandlerService;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.freakz.hokan_ng_springboot.bot.util.StaticStrings.ARG_COMMAND;

/**
 * User: petria
 * Date: 11/21/13
 * Time: 1:55 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Component
@Scope("prototype")
public class HelpCmd extends Cmd {

  @Autowired
  private CommandHandlerService commandHandler;

  public HelpCmd() {
    super();
    setHelp("Shows command list / help about specific command.");
    UnflaggedOption flg = new UnflaggedOption(ARG_COMMAND)
        .setRequired(false)
        .setGreedy(false);
    registerParameter(flg);
  }

  @Override
  public String getMatchPattern() {
    return "!help.*";
  }

  @Override
  public void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) throws HokanException {

    String command = results.getString(ARG_COMMAND);

    StringBuilder sb = new StringBuilder();

    Comparator<Cmd> comparator = new Comparator<Cmd>() {
      @Override
      public int compare(Cmd cmd1, Cmd cmd2) {
        return cmd1.getName().compareTo(cmd2.getName());
      }
    };

    if (command == null) {

      List<Cmd> commands = commandHandler.getCommandHandlers();
      Collections.sort(commands, comparator);

      sb.append("== HELP: Command List");
      sb.append("\n");

      for (Cmd cmd : commands) {

        if (cmd.isChannelOpOnly() && !isChannelOp && !isAdminUser) {
          continue;
        }

        if (cmd.isAdminUserOnly() && (!isAdminUser)) {
          continue;
        }

        sb.append("  ");
        sb.append(cmd.getName());
        String flags = "";
        if (cmd.toBotOnly) {
          flags += "B";
        }
        if (cmd.channelOpOnly) {
          flags += "C";
        }
        if (cmd.loggedInOnly) {
          flags += "L";
        }
        if (cmd.adminUserOnly) {
          flags += "A";
        }
        if (cmd.privateOnly) {
          flags += "P";
        }
        if (flags.length() > 0) {
          sb.append("[").append(flags).append("]");
        }
      }
      sb.append("\nTry '!help <command>' to get detailed help\n");
      sb.append("B: to bot only ");
      if (isAdminUser || isChannelOp) {
        sb.append("C: channel op only ");
      }
      sb.append("L: logged in only ");
      if (isAdminUser) {
        sb.append("A: admin user only ");
      }
      sb.append("P: private msg only");

    } else {
      List<Cmd> commands = commandHandler.getCommandHandlersByName(command);
      Collections.sort(commands, comparator);
      for (Cmd cmd : commands) {
        String help = String.format("HELP: %s (%s) -> %s%s",
            cmd.getName(), cmd.getMatchPattern(), cmd.getHelp(), buildSeeAlso(cmd));
        sb.append(help);
        sb.append("\n");
      }
    }
    response.setResponseMessage(sb.toString());

  }
}
