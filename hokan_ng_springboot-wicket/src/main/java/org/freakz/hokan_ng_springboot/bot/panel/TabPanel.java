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
public class TabPanel extends Panel {

    public TabPanel(String id) {
    super(id);
    List<ITab> tabs = new ArrayList<>();
    tabs.add(new AbstractTab(new Model<String>("User Settings")) {
      @Override
      public Panel getPanel(String panelId) {
        return new UserSettingsPanel(panelId);
      }
    });
    tabs.add(new AbstractTab(new Model<String>("Channels")) {
      @Override
      public Panel getPanel(String panelId) {
        return new ChannelsPanel(panelId);
      }
    });


    add(new AjaxTabbedPanel("tabs", tabs));

  }

}