package org.freakz.hokan_ng_springboot.bot.panel.status;

import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.image.resource.RenderedDynamicImageResource;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.time.Duration;
import org.freakz.hokan_ng_springboot.bot.Services;
import org.freakz.hokan_ng_springboot.bot.enums.HokanModule;
import org.freakz.hokan_ng_springboot.bot.model.HokanStatusModel;

import java.awt.*;
import java.util.Random;

/**
 * Created by Petri Airio on 9.4.2015.
 *
 */
public class HokanStatusPanel extends Panel {

  protected static final ResourceReference RESOURCE_REF_BLUE = new PackageResourceReference(HokanStatusPanel.class,
      "BlueBall_small.png");
  protected static final ResourceReference RESOURCE_REF_ORANGE = new PackageResourceReference(HokanStatusPanel.class,
      "OrangeBall_small.png");
  protected static final ResourceReference RESOURCE_REF_RED = new PackageResourceReference(HokanStatusPanel.class,
      "RedBall_small.png");

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
      graphics.setColor(Color.RED);
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


  public HokanStatusPanel(String id) {
    super(id);
    final HokanStatus engineStatus = new HokanStatus("engineStatus", HokanModule.HokanEngine);
    final HokanStatus ioStatus = new HokanStatus("ioStatus", HokanModule.HokanIo);
    final HokanStatus servicesStatus = new HokanStatus("servicesStatus", HokanModule.HokanServices);
    engineStatus.setOutputMarkupId(true);
    ioStatus.setOutputMarkupId(true);
    servicesStatus.setOutputMarkupId(true);
    add(engineStatus);
    add(ioStatus);
    add(servicesStatus);

    final NonCachingImage statusBall = new NonCachingImage("statusBall", new CircleDynamicImageResource(50, 50));
    statusBall.setOutputMarkupId(true);
    add(statusBall);

    final AbstractAjaxTimerBehavior timer = new AbstractAjaxTimerBehavior(Duration.seconds(3)) {
      @Override
      protected void onTimer(AjaxRequestTarget target) {
        statusBall.setImageResource(new CircleDynamicImageResource(50, 50));
        target.add(statusBall);
        target.add(engineStatus);
        target.add(ioStatus);
        target.add(servicesStatus);
      }
    };
    add(timer);

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
          return new Image("statusBall", new Model<>(new PackageResourceReference(HokanStatusPanel.class, "RedBall_small.png")));
        case "<online>":
          return new Image("statusBall", new Model<>(new PackageResourceReference(HokanStatusPanel.class, "BlueBall_small.png")));
        case "<unknown>":
          return new Image("statusBall", new Model<>(new PackageResourceReference(HokanStatusPanel.class, "OrangeBall_small.png")));
      }
      return null;
    }
  }

}
