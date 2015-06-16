package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.JSAPResult;
import org.freakz.hokan_ng_springboot.bot.enums.HokanModule;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.models.HokanStatusModel;
import org.freakz.hokan_ng_springboot.bot.service.HokanStatusService;
import org.freakz.hokan_ng_springboot.bot.util.JarScriptExecutor;
import org.freakz.hokan_ng_springboot.bot.util.Uptime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * User: petria
 * Date: 11/6/13
 * Time: 8:19 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Component
@Scope("prototype")
public class UptimeCmd extends Cmd {

  @Autowired
  private HokanStatusService statusService;

  public UptimeCmd() {
    super();
    setHelp("Shows system and bot uptime.");
  }

  @Override
  public String getMatchPattern() {
    return "!uptime.*";
  }

  @Override
  public String getName() {
    return "Uptime";
  }

  @Override
  public void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) throws HokanException {
    JarScriptExecutor cmdExecutor = new JarScriptExecutor("/uptime.sh", "UTF-8");
    String[] sysUptime = cmdExecutor.executeJarScript();
    String uptime1 = String.format("%15s     :%s\n", "System", sysUptime[0]);
    response.addResponse(uptime1);
    for (HokanModule module : HokanModule.values()) {
      HokanStatusModel statusModel = statusService.getHokanStatus(module);
      Uptime ut = statusModel.getPingResponse().getUptime();
      String moduleUptime = String.format("%15s     :%s\n", module, ut.toString());
      response.addResponse(moduleUptime);
    }
  }

}