package org.freakz.hokan_ng_springboot.bot.panel;

import org.apache.wicket.markup.html.panel.Panel;
import org.freakz.hokan_ng_springboot.bot.Services;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Network;

/**
 * Created by Petri Airio on 24.3.2015.
 *
 */
public class ConfigPanel extends Panel {

  public ConfigPanel(String id) {
    super(id);
    add(new EditableNetworksPanel("editableNetworksPanel"));
    Network network = Services.getNetworkService().findAll().get(0);
    add(new EditableChannelsPanel("editableChannelsPanel", network));
  }

}
