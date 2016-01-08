package org.freakz.hokan_ng_springboot.bot.pages;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.freakz.hokan_ng_springboot.bot.panel.liveirc.LiveIrcChannelPanel;

/**
 * Created by Petri Airio on 29.12.2015.
 * -
 */
@AuthorizeInstantiation("USER")
public class LiveIrcChannelPage extends BasePage {

  public LiveIrcChannelPage(PageParameters params) {
    super(params);
    add(new LiveIrcChannelPanel("liveIrcChannelPanel"));

  }

}
