package org.freakz.hokan_ng_springboot.bot.panel.status;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.freakz.hokan_ng_springboot.bot.Services;
import org.freakz.hokan_ng_springboot.bot.enums.HokanModule;

/**
 * Created by Petri Airio on 9.4.2015.
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
      String status = Services.getHokanStatusService().getHokanStatus(hokanModule);
      return String.format("%s : %s", hokanModule, status);
    }
  }

}
