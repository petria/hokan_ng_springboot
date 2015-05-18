package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.JSAPResult;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.jpa.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by Petri Airio on 18.5.2015.
 *
 */
@Component
@Scope("prototype")
@Slf4j
public class PsCmd extends Cmd {

  @Autowired
  private PropertyService propertyService;

  public PsCmd() {
    super();
    setHelp("Shows active processes running in Bot.");
    addToHelpGroup(HelpGroup.PROCESS, this);
  }

  @Override
  public void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) throws HokanException {
// TODO
  }
}
