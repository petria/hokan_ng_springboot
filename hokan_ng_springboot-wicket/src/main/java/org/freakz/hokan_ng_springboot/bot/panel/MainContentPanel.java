package org.freakz.hokan_ng_springboot.bot.panel;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Created by Petri Airio on 20.3.2015.
 *
 */
public class MainContentPanel extends Panel {

  public MainContentPanel(String id, WebPage page) {
    super(id);

/*    List<ITab> tabs = new ArrayList<>();
    tabs.add(new AbstractTab(new Model<>("Status")) {
      @Override
      public Panel getPanel(String panelId) {
        return new StatusPanel(panelId);
      }
    });
    tabs.add(new AbstractTab(new Model<>("UserSettings")) {
      @Override
      public Panel getPanel(String panelId) {
        return new UserSettingsPanel(panelId);
      }
    });
    add(new AjaxTabbedPanel<ITab>("tabs", tabs));
*/
  }

}
