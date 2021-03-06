package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.JSAPResult;
import org.freakz.hokan_ng_springboot.bot.command.HelpGroup;
import org.freakz.hokan_ng_springboot.bot.command.annotation.HelpGroups;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.events.ServiceRequestType;
import org.freakz.hokan_ng_springboot.bot.events.ServiceResponse;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.models.DataUpdaterModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Petri Airio on 17.6.2015.
 *
 */
@Component
@Scope("prototype")
@HelpGroups(
    helpGroups = {HelpGroup.UPDATERS}
)
public class UpdaterListCmd extends Cmd {

  public UpdaterListCmd() {
    super();
    setHelp("Shows DataUpdaters and their status / update count / next update");
  }

  @Override
  public void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) throws HokanException {
    ServiceResponse serviceResponse = doServicesRequest(ServiceRequestType.UPDATERS_LIST_REQUEST, request.getIrcEvent(), ".*");
    List<DataUpdaterModel> modelList = serviceResponse.getUpdaterListData();
    String header = String.format("%20s - %3s - %-28s - %s\n", "Updater Name", "Cnt", "Next Update", "Status");
    response.addResponse(header);
    for (DataUpdaterModel model : modelList) {
      String txt = String.format("%20s - %3d - %28s - %s\n", model.getName(), model.getCount(), model.getNextUpdate(), model.getStatus());
      response.addResponse(txt);
    }
  }

}
