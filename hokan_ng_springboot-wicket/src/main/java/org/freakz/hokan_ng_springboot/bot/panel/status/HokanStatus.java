package org.freakz.hokan_ng_springboot.bot.panel.status;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.freakz.hokan_ng_springboot.bot.Services;
import org.freakz.hokan_ng_springboot.bot.enums.HokanModule;
import org.freakz.hokan_ng_springboot.bot.model.HokanStatusModel;

/**
 * Created by Petri Airio on 9.4.2015.
 *
 */
public class HokanStatus extends Panel {

  protected static HokanStatusPanel hokanStatusPanel;

  public HokanStatus(String id, HokanModule hokanModule, HokanStatusPanel hokanStatusPanel) {
    super(id);
    this.hokanStatusPanel = hokanStatusPanel;
//    add(new HokanStatusBall("statusBall", hokanModule));
    add(new Image("statusBall", new PackageResourceReference(HokanStatus.class, "OrangeBall_small.png")));

    add(new HokanStatusLabel("statusLabel", hokanModule));
  }

  public static class HokanStatusBall extends Image {

    protected HokanStatusBall(String id, HokanModule hokanModule) {
      super(id, new ImageModel(hokanModule));
    }

    private static class ImageModel extends AbstractReadOnlyModel<Image> {


      private final HokanModule hokanModule;

      public ImageModel(HokanModule hokanModule) {
        this.hokanModule = hokanModule;
      }

      @Override
      public Image getObject() {
        HokanStatusModel status = Services.getHokanStatusService().getHokanStatus(hokanModule);
        switch (status.getStatus()) {
          case "<offline>":
            return new Image("statusBall", new Model<>(hokanStatusPanel.RESOURCE_REF_RED));
          case "<online>":
            return new Image("statusBall", new Model<>(hokanStatusPanel.RESOURCE_REF_BLUE));
          case "<unknown>":
            return new Image("statusBall", new Model<>(hokanStatusPanel.RESOURCE_REF_ORANGE));
        }
        return null;
      }
    }

  }

  public static class HokanStatusLabel extends Label {

    HokanStatusLabel(String id, HokanModule hokanModule) {
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


}
