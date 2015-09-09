package org.freakz.hokan_ng_springboot.bot.example;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.freakz.hokan_ng_springboot.bot.panel.users.UsersPanel;

/**
 * Created by Petri Airio on 9.9.2015.
 *
 */
public class UsersPage extends BasePage {

  private UsersPanel usersPanel;

  public UsersPage(final PageParameters params) {
    super(params);
    addPanels();
  }

  private void addPanels() {
    usersPanel = new UsersPanel("usersPanel");
    add(usersPanel);
  }

}
