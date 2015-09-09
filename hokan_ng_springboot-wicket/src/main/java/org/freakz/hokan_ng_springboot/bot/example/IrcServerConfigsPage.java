package org.freakz.hokan_ng_springboot.bot.example;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.freakz.hokan_ng_springboot.bot.panel.EditableIrcServerConfigPanel;

/**
 * Created by Petri Airio on 9.9.2015.
 *
 */
public class IrcServerConfigsPage extends BasePage {

  final private EditableIrcServerConfigPanel editableIrcServerConfigPanel;

  public IrcServerConfigsPage(PageParameters params) {
    super(params);
    editableIrcServerConfigPanel = new EditableIrcServerConfigPanel("editableIrcServerConfigPanel");
    add(editableIrcServerConfigPanel);
  }

}
