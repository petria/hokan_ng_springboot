package org.freakz.hokan_ng_springboot.bot.panel;

import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 22.3.2015.
 *
 */
public class MainTabPanel extends Panel {

  public MainTabPanel(String id) {
    super(id);
    List<ITab> tabs = new ArrayList<>();
    tabs.add(new AbstractTab(new Model<>("Configuration")) {
      @Override
      public Panel getPanel(String panelId) {
        return new ConfigurationsTabPanel(panelId);
      }
    });
    tabs.add(new AbstractTab(new Model<>("Runtime control")) {
      @Override
      public Panel getPanel(String panelId) {
        return new RuntimeControlPanel(panelId);
      }
    });
    add(new AjaxTabbedPanel<>("mainTabPanel", tabs));

  }

}
