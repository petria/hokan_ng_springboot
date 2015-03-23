package org.freakz.hokan_ng_springboot.bot.page;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.freakz.hokan_ng_springboot.bot.page2.WicketExamplePage;
import org.freakz.hokan_ng_springboot.bot.panel.HeaderPanel;
import org.freakz.hokan_ng_springboot.bot.panel.TabPanel;

/**
 * Created by Petri Airio on 20.3.2015.
 *
 */
@AuthorizeInstantiation("USER")
public class HokanBasePage extends WicketExamplePage {

  public HokanBasePage(final PageParameters parameters) {
    add(new HeaderPanel("headerPanel", this));
    add(new TabPanel("tabPanel"));
  }

}
