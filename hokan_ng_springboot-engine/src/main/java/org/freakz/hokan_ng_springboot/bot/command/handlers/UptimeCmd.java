package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.JSAPResult;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.util.JarScriptExecutor;
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
/*    long coreIoUptime = properties.getPropertyAsLong(PropertyName.PROP_SYS_CORE_IO_UPTIME, 0);
    long coreIoRuntime = properties.getPropertyAsLong(PropertyName.PROP_SYS_CORE_IO_RUNTIME, 0);
    long coreEngineUptime = properties.getPropertyAsLong(PropertyName.PROP_SYS_CORE_ENGINE_UPTIME, 0);
    long coreEngineRuntime = properties.getPropertyAsLong(PropertyName.PROP_SYS_CORE_ENGINE_RUNTIME, 0);
    Uptime ut1 = new Uptime(coreIoUptime);
    Uptime ut2 = new Uptime(coreEngineUptime);
        response.addResponse(uptime2);
    response.addResponse(uptime3);


    String uptime2 = String.format("Core-io    : %s (total runtime: %d sec)\n", ut1.toString(), coreIoRuntime);
    String uptime3 = String.format("Core-engine: %s (total runtime: %d sec)", ut2.toString(), coreEngineRuntime);

    */
    String uptime1 = String.format("System     :%s\n", sysUptime[0]);
    response.addResponse(uptime1);
/*    for (HokanModule module : HokanModule.values()) {
      ObjectMessage objectMessage = jmsSender.sendAndGetReply(module.getQueueName(), "COMMAND", "PING", false);
      if (objectMessage == null) {
        statusModelMap.put(module, new HokanStatusModel("<offline>"));
        continue;
      }
      try {
        JmsMessage jmsMessage = (JmsMessage) objectMessage.getObject();
        PingResponse pingResponse = (PingResponse) jmsMessage.getPayLoadObject("PING_RESPONSE");
        HokanStatusModel status = new HokanStatusModel("<online>");
        status.setPingResponse(pingResponse);
        statusModelMap.put(module, status);
      } catch (JMSException e) {
        log.error("jms", e);
      }
    }
*/

  }

}
