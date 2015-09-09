package org.freakz.hokan_ng_springboot.bot.example;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.freakz.hokan_ng_springboot.bot.panel.EditableNetworksPanel;

/**
 * Created by Petri Airio on 9.9.2015.
 *
 */
public class NetworkConfigsPage extends BasePage {

  final private EditableNetworksPanel editableNetworksPanel;

  public NetworkConfigsPage(PageParameters params) {
    super(params);
    editableNetworksPanel = new EditableNetworksPanel("editableNetworksPanel");
    add(editableNetworksPanel);
  }

}
