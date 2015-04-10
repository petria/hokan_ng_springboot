package org.freakz.hokan_ng_springboot.bot.panel.status;

import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.time.Duration;
import org.freakz.hokan_ng_springboot.bot.enums.HokanModule;

/**
 * Created by Petri Airio on 9.4.2015.
 *
 */
public class HokanStatusPanel extends Panel {

  protected static final ResourceReference RESOURCE_REF_BLUE = new PackageResourceReference(HokanStatus.class,
      "BlueBall_small.png");
  protected static final ResourceReference RESOURCE_REF_ORANGE = new PackageResourceReference(HokanStatus.class,
      "OrangeBall_small.png");
  protected static final ResourceReference RESOURCE_REF_RED = new PackageResourceReference(HokanStatus.class,
      "RedBall_small.png");

  public HokanStatusPanel(String id) {
    super(id);
    final HokanStatus engineStatus = new HokanStatus("engineStatus", HokanModule.HokanEngine, this);
    final HokanStatus ioStatus = new HokanStatus("ioStatus", HokanModule.HokanIo, this);
    final HokanStatus servicesStatus = new HokanStatus("servicesStatus", HokanModule.HokanServices, this);

    engineStatus.setOutputMarkupId(true);
    ioStatus.setOutputMarkupId(true);
    servicesStatus.setOutputMarkupId(true);

    add(engineStatus);
    add(ioStatus);
    add(servicesStatus);

    final AbstractAjaxTimerBehavior timer = new AbstractAjaxTimerBehavior(Duration.seconds(3)) {
      @Override
      protected void onTimer(AjaxRequestTarget target) {
        target.add(engineStatus);
        target.add(ioStatus);
        target.add(servicesStatus);
      }
    };
    add(timer);

  }

}
