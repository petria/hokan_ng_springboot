package org.freakz.hokan_ng_springboot.bot.panel.status;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.freakz.hokan_ng_springboot.bot.Services;
import org.freakz.hokan_ng_springboot.bot.enums.HokanModule;
import org.freakz.hokan_ng_springboot.bot.model.HokanStatusModel;

/**
 * Created by Petri Airio on 9.4.2015.
 *
 */
public class HokanStatus extends Label {

  public HokanStatus(String id, HokanModule hokanModule) {
    super(id, new StatusModel(hokanModule));
  }

  private static class StatusModel extends AbstractReadOnlyModel<String> {

    private final HokanModule hokanModule;

    public StatusModel(HokanModule hokanModule) {
      this.hokanModule = hokanModule;
    }

    @Override
    public String getObject() {
      HokanStatusModel status = Services.getHokanStatusService().getHokanStatus(hokanModule);
      String uptime = "";
      if (status.getPingResponse() != null) {
        uptime = " :: " + status.getPingResponse().getUptime().toString();
      }
      return String.format("%s : %s%s", hokanModule, status.getStatus(), uptime);
    }
  }

}
