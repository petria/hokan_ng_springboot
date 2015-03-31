package org.freakz.hokan_ng_springboot.bot.panel;

import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.freakz.hokan_ng_springboot.bot.MyAuthenticatedWebSession;
import org.freakz.hokan_ng_springboot.bot.page.HokanBasePage;
import org.freakz.hokan_ng_springboot.bot.panel.usereditor.UserEditorPanel;

/**
 * Created by Petri Airio on 24.3.2015.
 *
 */
@Slf4j
public class LoggedInUserPanel extends Panel {

  public LoggedInUserPanel() {
    super("loggedInUserPanel");
    final MyAuthenticatedWebSession session = (MyAuthenticatedWebSession) AuthenticatedWebSession.get();
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

    final ModalWindow modal2;
    add(modal2 = new ModalWindow("modal2"));

    modal2.setContent(new UserEditorPanel(modal2.getContentId(), session.getUser()));
    modal2.setTitle("User editor.");
    modal2.setCookieName("modal-2");

    modal2.setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {
      @Override
      public boolean onCloseButtonClicked(AjaxRequestTarget target) {
        log.debug("onCloseButtonClicked");
        return true;
      }
    });

    modal2.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
      @Override
      public void onClose(AjaxRequestTarget target) {
        log.debug("onClose");
//        target.add(this);
      }
    });

    AjaxLink<Void> userEditorLink = new AjaxLink<Void>("userEditorLink") {
      @Override
      public void onClick(AjaxRequestTarget target) {
        modal2.show(target);
      }
    };
    userEditorLink.add(new Label("userNameLabel", session.getUser()));
    add(userEditorLink);

  }

}
