package org.freakz.hokan_ng_springboot.bot.panel;

import org.apache.wicket.markup.html.panel.Panel;
import org.freakz.hokan_ng_springboot.bot.panel.status.HokanStatusPanel;

/**
 * Created by Petri Airio on 27.3.2015.
 *
 */
public class RuntimeControlPanel extends Panel {

  public RuntimeControlPanel(String id) {

    super(id);
    add(new HokanStatusPanel("statusPanel"));

  }

}
