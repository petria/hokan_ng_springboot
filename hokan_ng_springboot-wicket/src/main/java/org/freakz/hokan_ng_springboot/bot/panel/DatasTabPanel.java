package org.freakz.hokan_ng_springboot.bot.panel;

import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.freakz.hokan_ng_springboot.bot.panel.urls.UrlsPanel;
import org.freakz.hokan_ng_springboot.bot.panel.users.UsersPanel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 16.4.2015.
 *
 */
public class DatasTabPanel extends Panel {

  public DatasTabPanel(String id) {
    super(id);
    List<ITab> tabs = new ArrayList<>();
    tabs.add(new AbstractTab(new Model<>("UrlsPanel")) {
      @Override
      public Panel getPanel(String panelId) {
        return new UrlsPanel(panelId);
      }
    });
    tabs.add(new AbstractTab(new Model<>("UsersPanel")) {
      @Override
      public Panel getPanel(String panelId) {
        return new UsersPanel(panelId);
      }
    });
    add(new AjaxTabbedPanel<>("tabs", tabs));
  }

}
