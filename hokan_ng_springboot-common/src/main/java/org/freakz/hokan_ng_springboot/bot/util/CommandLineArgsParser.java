package org.freakz.hokan_ng_springboot.bot.util;

import org.freakz.hokan_ng_springboot.bot.enums.CommandLineArgs;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Petri Airio on 13.11.2015.
 *
 */
public class CommandLineArgsParser {

  final private String[] args;

  public CommandLineArgsParser(String[] args) {
    this.args = args;
  }

  public Map<CommandLineArgs, String> parseArgs() {
    Map<CommandLineArgs, String> parsedArgs = new HashMap<>();
    if (args != null) {
      for (String arg : this.args) {
        if (arg.startsWith("--") && arg.contains("=")) {
          String[] split = arg.split("=");
          for (CommandLineArgs commandLineArgs : CommandLineArgs.values()) {
            if (split[0].equalsIgnoreCase(commandLineArgs.getCommandLineArg())) {
              parsedArgs.put(commandLineArgs, split[1]);
            }
          }
        }
      }
    }
    return parsedArgs;
  }


}
