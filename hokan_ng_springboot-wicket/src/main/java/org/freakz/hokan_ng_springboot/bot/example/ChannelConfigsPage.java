package org.freakz.hokan_ng_springboot.bot.example;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.freakz.hokan_ng_springboot.bot.Services;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Network;
import org.freakz.hokan_ng_springboot.bot.panel.EditableChannelsPanel;

/**
 * Created by Petri Airio on 9.9.2015.
 *
 */
public class ChannelConfigsPage extends BasePage {

  private EditableChannelsPanel editableChannelsPanel;

  public ChannelConfigsPage(PageParameters params) {
    super(params);
    Network network = Services.getNetworkService().findAll().get(0); // TODO
    editableChannelsPanel = new EditableChannelsPanel("editableChannelsPanel", network);
    add(editableChannelsPanel);
  }

}
