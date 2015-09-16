package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.JSAPResult;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.command.annotation.HelpGroups;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.util.JarScriptExecutor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * User: petria
 * Date: 12/13/13
 * Time: 9:33 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Component
@Scope("prototype")
@Slf4j
@HelpGroups(
    helpGroups = {HelpGroup.SYSTEM}
)
public class HostInfoCmd extends Cmd {

  public HostInfoCmd() {
    super();
    setHelp("Shows information about the host machine where the Bot is running on.");
  }

  @Override
  public void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) throws HokanException {

    JarScriptExecutor cmdExecutor = new JarScriptExecutor("/hostinfo.sh", "UTF-8");
    String[] hostInfo = cmdExecutor.executeJarScript();

    response.addResponse("I am running on: %s", hostInfo[0]);
  }

}
