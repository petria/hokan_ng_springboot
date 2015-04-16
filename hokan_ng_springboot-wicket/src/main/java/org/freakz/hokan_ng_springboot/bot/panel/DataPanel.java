package org.freakz.hokan_ng_springboot.bot.panel;

import org.apache.wicket.markup.html.panel.Panel;
import org.freakz.hokan_ng_springboot.bot.panel.urls.UrlsPanel;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 16.4.2015.
 *
 */
public class DataPanel extends Panel {

  public DataPanel(String id) {
    super(id);
    add(new UrlsPanel("urlsPanel"));
  }

}
