package org.freakz.hokan_ng_springboot.bot.panel.status;

import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.util.time.Duration;
import org.freakz.hokan_ng_springboot.bot.enums.HokanModule;

/**
 * Created by Petri Airio on 9.4.2015.
 */
public class HokanStatusPanel extends Panel {

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

    final AbstractAjaxTimerBehavior timer = new AbstractAjaxTimerBehavior(Duration.seconds(1)) {
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
