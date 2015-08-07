package org.freakz.hokan_ng_springboot.bot.example;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.freakz.hokan_ng_springboot.bot.panel.urls.UrlsPanel;

/**
 * Created by Petri Airio on 7.8.2015.
 *
 */
public class UrlsPage extends BasePage {

  private UrlsPanel urlsPanel;

  public UrlsPage(final PageParameters parameters) {
    super(parameters);

    addPanels();
//    addLink();
//    add(new Label("version", getApplication().getFrameworkSettings().getVersion()));
  }

  private void addPanels() {
    urlsPanel =  new UrlsPanel("urlsPanel");
    add(urlsPanel);
  }

}
