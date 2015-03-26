package org.freakz.hokan_ng_springboot.bot.panel;

import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.freakz.hokan_ng_springboot.bot.Services;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petri Airio on 26.3.2015.
 *
 */
public class ConfigChannelPropertiesTabPanel extends Panel {

  public ConfigChannelPropertiesTabPanel(String id) {
    super(id);
    List<ITab> tabs = new ArrayList<>();
    for (Channel channel : Services.getChannelService().findAll()) {
      tabs.add(new AbstractTab(new Model<String>(channel.toString())) {
        @Override
        public Panel getPanel(String panelId) {
          return new EditableChannelPropertiesPanel(panelId, channel);
        }
      });

    }
    add(new AjaxTabbedPanel<>("configChannelPropertiesTabs", tabs));

  }
}



