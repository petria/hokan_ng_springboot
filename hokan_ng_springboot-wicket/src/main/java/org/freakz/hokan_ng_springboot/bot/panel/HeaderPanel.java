package org.freakz.hokan_ng_springboot.bot.panel;

import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.Panel;
import org.freakz.hokan_ng_springboot.bot.MyAuthenticatedWebSession;
import org.freakz.hokan_ng_springboot.bot.page.HokanBasePage;

/**
 * Created by Petri Airio on 20.3.2015.
 *
 */
@Slf4j
public class HeaderPanel extends Panel {

  public HeaderPanel(String id, WebPage page) {
    super(id);
    AjaxLink<Void> logoutLink = new AjaxLink<Void>("logoutLink") {
      @Override
      public void onClick(AjaxRequestTarget ajaxRequestTarget) {
        log.debug("Logging off!");
        MyAuthenticatedWebSession session = (MyAuthenticatedWebSession) AuthenticatedWebSession.get();
        session.logoffUser();
        getSession().invalidate();
        setResponsePage(HokanBasePage.class);
      }
    };
    add(logoutLink);
  }

}
