package org.freakz.hokan_ng_springboot.bot.panel;

import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.freakz.hokan_ng_springboot.bot.Services;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Network;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petri Airio on 26.3.2015.
 */
public class ConfigChannelsTabPanel extends Panel {
  public ConfigChannelsTabPanel(String id) {
    super(id);

    List<ITab> tabs = new ArrayList<>();
    for (Network network : Services.getNetworkService().findAll()) {
      tabs.add(new AbstractTab(new Model<String>(network.toString())) {
        @Override
        public Panel getPanel(String panelId) {
          return new EditableChannelsPanel(panelId, network);
        }
      });

    }
    add(new AjaxTabbedPanel<>("configChannelTabs", tabs));

  }
}
