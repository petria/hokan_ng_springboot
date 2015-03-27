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
 */
public class ConfigurationsTabPanel extends Panel {

  public ConfigurationsTabPanel(String id) {
    super(id);
    List<ITab> tabs = new ArrayList<>();
    tabs.add(new AbstractTab(new Model<>("Networks")) {
      @Override
      public Panel getPanel(String panelId) {
        return new EditableNetworksPanel(panelId);
      }
    });
    tabs.add(new AbstractTab(new Model<>("Channels")) {
      @Override
      public Panel getPanel(String panelId) {
        return new ConfigChannelsTabPanel(panelId);
      }
    });
    tabs.add(new AbstractTab(new Model<>("Channel properties")) {
      @Override
      public Panel getPanel(String panelId) {
        return new ConfigChannelPropertiesTabPanel(panelId);
      }
    });
    tabs.add(new AbstractTab(new Model<>("System properties")) {
      @Override
      public Panel getPanel(String panelId) {
        return new EditableSystemPropertiesPanel(panelId);
      }
    });

    add(new AjaxTabbedPanel<>("tabs", tabs));

  }

}
