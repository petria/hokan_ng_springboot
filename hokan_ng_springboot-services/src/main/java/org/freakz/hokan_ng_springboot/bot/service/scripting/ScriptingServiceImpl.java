package org.freakz.hokan_ng_springboot.bot.service.scripting;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.events.ServiceRequest;
import org.freakz.hokan_ng_springboot.bot.events.ServiceRequestType;
import org.freakz.hokan_ng_springboot.bot.events.ServiceResponse;
import org.freakz.hokan_ng_springboot.bot.models.ScriptResult;
import org.freakz.hokan_ng_springboot.bot.service.annotation.ServiceMessageHandler;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Petri Airio on 5.4.2016.
 * -
 */
@Service
@Slf4j
public class ScriptingServiceImpl implements ScriptingService {

  private static final int TIMEOUT_SEC = 60;
  private static final ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");

  private ScriptResult execWithThread(final ScriptEngine engine, final String script, ServiceRequest request) {
    ScriptResult scriptResult = new ScriptResult();
    final Runnable r = new Runnable() {
      public void run() {
        try {
          StringWriter sw = new StringWriter();
          PrintWriter pw = new PrintWriter(sw);

          engine.getContext().setWriter(pw);
          log.debug("Script eval()");
          engine.put("ims", request.getIrcMessageEvent());
          Object res = engine.eval(script);
          log.debug("Script result: {}", res);
          scriptResult.setScriptOutput(sw.getBuffer().toString());
        } catch (ScriptException e) {
          scriptResult.setScriptOutput(e.getMessage());
          log.debug("Java: Caught exception from eval(): " + e.getMessage());
        }
      }
    };
    log.debug("Java: Starting thread...");
    final Thread t = new Thread(r);
    t.start();

    long startTime = System.currentTimeMillis();
    boolean doWait = true;
    while (doWait) {
      try {
        t.join(1000);
        if (t.isAlive()) {
          long currentTime = System.currentTimeMillis();
          long diff = (currentTime - startTime) / 1000L;
          if (diff > 10) {
            scriptResult.setScriptOutput("Script timed out!");
            log.debug("Timeout waiting scrip to finish!");
            t.stop();
            doWait = false;
          }
        } else {
          doWait = false;
        }
      } catch (InterruptedException e) {
//        log.debug("*");
        e.printStackTrace();
        doWait = false;
      }
    }

    log.debug("Script run ended!");

/*    log.debug("Java: ...thread started, timeout: {}", TIMEOUT_SEC);
    try {
      Thread.sleep(TIMEOUT_SEC * 1000);
      if (t.isAlive()) {
        log.debug("Java: Thread alive after timeout, stopping...");
        t.stop();
        log.debug("Java: ...thread stopped");
      } else {
        log.debug("Java: Thread not alive after timeout.");
      }
    } catch (InterruptedException e) {
      log.debug("Interrupted while waiting for timeout to elapse.");
    }*/
    return scriptResult;
  }


  @Override
  public ScriptResult evalScript(String script, ServiceRequest request) {
    ScriptResult result = execWithThread(engine, script, request);
    return result;
  }

  @ServiceMessageHandler(ServiceRequestType = ServiceRequestType.SCRIPT_SERVICE_REQUEST)
  public void handleLunchPlacesServiceRequest(ServiceRequest request, ServiceResponse response) {
    String script = (String) request.getParameters()[0];
    ScriptResult result = evalScript(script, request);
    response.setResponseData(request.getType().getResponseDataKey(), result);
  }

}