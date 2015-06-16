package org.freakz.hokan_ng_springboot.bot.panel.status;

import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.image.resource.RenderedDynamicImageResource;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.util.time.Duration;
import org.freakz.hokan_ng_springboot.bot.Services;
import org.freakz.hokan_ng_springboot.bot.enums.HokanModule;
import org.freakz.hokan_ng_springboot.bot.models.HokanStatusModel;

import java.awt.*;
import java.util.Random;

/**
 * Created by Petri Airio on 9.4.2015.
 *
 */
public class HokanStatusPanel extends Panel {


  private final class CircleDynamicImageResource extends RenderedDynamicImageResource {

    private HokanModule module;

    private CircleDynamicImageResource(int width, int height, HokanModule hokanModule) {
      super(width, height);
      this.module = hokanModule;
    }

    @Override
    protected boolean render(Graphics2D graphics, Attributes attributes) {
      drawCircle(graphics);
      return true;
    }

    void drawCircle(Graphics2D graphics) {
      // Compute random size for circle
      HokanStatusModel status = Services.getHokanStatusService().getHokanStatus(this.module);
      if (status.getStatus().contains("unknown")) {
        graphics.setColor(Color.YELLOW);
      } else if (status.getStatus().contains("offline")) {
        graphics.setColor(Color.RED);
      } else if (status.getStatus().contains("online")) {
        graphics.setColor(Color.GREEN);
      }
      graphics.setBackground(Color.WHITE);

      final Random random = new Random();
      int dx = 6;
      int dy = 6;
      int x = 5;
      int y = 5;

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

    final NonCachingImage engineBall = new NonCachingImage("engineBall", new CircleDynamicImageResource(16, 16, HokanModule.HokanEngine));
    engineBall.setOutputMarkupId(true);
    add(engineBall);
    final NonCachingImage ioBall = new NonCachingImage("ioBall", new CircleDynamicImageResource(16, 16, HokanModule.HokanIo));
    ioBall.setOutputMarkupId(true);
    add(ioBall);
    final NonCachingImage servicesBall = new NonCachingImage("servicesBall", new CircleDynamicImageResource(16, 16, HokanModule.HokanServices));
    servicesBall.setOutputMarkupId(true);
    add(servicesBall);

    final AbstractAjaxTimerBehavior timer = new AbstractAjaxTimerBehavior(Duration.seconds(3)) {
      @Override
      protected void onTimer(AjaxRequestTarget target) {
        engineBall.setImageResource(new CircleDynamicImageResource(16, 16, HokanModule.HokanEngine));
        ioBall.setImageResource(new CircleDynamicImageResource(16, 16, HokanModule.HokanIo));
        servicesBall.setImageResource(new CircleDynamicImageResource(16, 16, HokanModule.HokanServices));

        target.add(engineBall);
        target.add(ioBall);
        target.add(servicesBall);

        target.add(engineStatus);
        target.add(ioStatus);
        target.add(servicesStatus);
      }
    };
    add(timer);

  }

}
