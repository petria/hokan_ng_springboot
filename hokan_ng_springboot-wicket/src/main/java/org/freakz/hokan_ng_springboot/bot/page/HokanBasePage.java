package org.freakz.hokan_ng_springboot.bot.page;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.freakz.hokan_ng_springboot.bot.page2.WicketExamplePage;
import org.freakz.hokan_ng_springboot.bot.panel.HeaderPanel;
import org.freakz.hokan_ng_springboot.bot.panel.StatusPanel;

/**
 * Created by Petri Airio on 20.3.2015.
 *
 */
public class HokanBasePage extends WicketExamplePage {

  public HokanBasePage(final PageParameters parameters) {
    add(new HeaderPanel("headerPanel", this));
    add(new StatusPanel("tabPanel"));
  }


}
