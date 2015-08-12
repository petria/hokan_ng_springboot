package org.freakz.hokan_ng_springboot.bot.example;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.freakz.hokan_ng_springboot.bot.panel.EditableSystemPropertiesPanel;

/**
 * Created by Petri Airio on 12.8.2015.
 *
 */
public class SystemPropertiesPage extends BasePage {

  private EditableSystemPropertiesPanel panel;

  public SystemPropertiesPage(final PageParameters params) {
    super(params);
    this.panel = new EditableSystemPropertiesPanel("panel");
    add(panel);
  }

}
