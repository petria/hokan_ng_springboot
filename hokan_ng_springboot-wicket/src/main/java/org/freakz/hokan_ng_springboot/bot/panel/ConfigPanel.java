package org.freakz.hokan_ng_springboot.bot.panel;

import org.apache.wicket.markup.html.panel.Panel;

/**
 * Created by Petri Airio on 24.3.2015.
 *
 */
public class ConfigPanel extends Panel {

  public ConfigPanel(String id) {
    super(id);
    setOutputMarkupId(true);
    add(new EditableNetworksPanel("editableNetworksPanel"));
  }

}
