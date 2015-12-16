package org.freakz.hokan_ng_springboot.bot.panel.status;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.resource.RenderedDynamicImageResource;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.freakz.hokan_ng_springboot.bot.Services;
import org.freakz.hokan_ng_springboot.bot.enums.HokanModule;
import org.freakz.hokan_ng_springboot.bot.models.HokanStatusModel;

import java.awt.*;
import java.util.Random;

/**
 * Created by Petri Airio on 9.4.2015.
 *
 */
public class HokanStatus extends Panel {

  public HokanStatus(String id, HokanModule hokanModule) {
    super(id);
    setOutputMarkupId(true);
    add(new HokanStatusLabel("statusLabel", hokanModule));
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
/*        HokanStatusModel status = Services.getHokanStatusService().getHokanStatus(hokanModule);
        String uptime = "";
        if (status.getPingResponse() != null) {
          uptime = " :: " + status.getPingResponse().getUptime().toString();
        }
        return String.format("%s : %s%s", hokanModule, status.getStatus(), uptime);
        TODO
        */
        return String.format("%s : %s%s", hokanModule, "TDO", "TODO");
      }
    }
  }

  private final class CircleDynamicImageResource extends RenderedDynamicImageResource {
    private CircleDynamicImageResource(int width, int height) {
      super(width, height);
    }

    @Override
    protected boolean render(Graphics2D graphics, Attributes attributes) {
      drawCircle(graphics);
      return true;
    }

    void drawCircle(Graphics2D graphics) {
      // Compute random size for circle
      graphics.setColor(Color.BLUE);
      final Random random = new Random();
      int dx = Math.abs(10 + random.nextInt(80));
      int dy = Math.abs(10 + random.nextInt(80));
      int x = Math.abs(random.nextInt(100 - dx));
      int y = Math.abs(random.nextInt(100 - dy));

      // Draw circle with thick stroke width
      graphics.setStroke(new BasicStroke(5));
      graphics.drawOval(x, y, dx, dy);
    }

  }


}
