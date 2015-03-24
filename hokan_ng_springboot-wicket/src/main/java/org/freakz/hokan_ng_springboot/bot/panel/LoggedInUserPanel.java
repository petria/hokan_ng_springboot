package org.freakz.hokan_ng_springboot.bot.panel;

import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.freakz.hokan_ng_springboot.bot.MyAuthenticatedWebSession;
import org.freakz.hokan_ng_springboot.bot.page.HokanBasePage;

/**
 * Created by Petri Airio on 24.3.2015.
 */
@Slf4j
public class LoggedInUserPanel extends Panel {

  public LoggedInUserPanel() {
    super("loggedInUserPanel");
    final MyAuthenticatedWebSession session = (MyAuthenticatedWebSession) AuthenticatedWebSession.get();
    add(new Label("userNameLabel", session.getUser()));
    AjaxLink<Void> logoutLink = new AjaxLink<Void>("logoutLink") {
      @Override
      public void onClick(AjaxRequestTarget ajaxRequestTarget) {
        log.debug("Logging off!");
        session.logoffUser();
        getSession().invalidate();
        setResponsePage(HokanBasePage.class);
      }
    };
    add(logoutLink);

  }

}
