/*
 * Copyright 2013 David Beer.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.freakz.hokan_ng_springboot.bot.example;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.dropdown.DropDownButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.dropdown.MenuBookmarkablePageLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.GlyphIconType;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.*;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;
import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.freakz.hokan_ng_springboot.bot.HokanAuthenticatedWebSession;

import java.util.ArrayList;
import java.util.List;

/**
 */
@Slf4j
public class HeaderPanel extends Panel {

  public HeaderPanel(String id) {
    super(id);
    add(navbar());
  }

  private Navbar navbar() {
    final HokanAuthenticatedWebSession session = (HokanAuthenticatedWebSession) AuthenticatedWebSession.get();

    Navbar navbar = new Navbar("navbar");
    navbar.setInverted(true);
    navbar.setPosition(Navbar.Position.TOP);
    navbar.setBrandName(Model.of("Hokan the Bot"));

    DropDownButton dropdown = new NavbarDropDownButton(Model.of("Datas")) {

      @Override
      protected List<AbstractLink> newSubMenuButtons(String buttonMarkupId) {
        final List<AbstractLink> subMenu = new ArrayList<AbstractLink>();
        subMenu.add(new MenuBookmarkablePageLink(UrlsPage.class, Model.of("Urls"))
            .setIconType(FontAwesomeIconType.user));

        subMenu.add(new MenuBookmarkablePageLink(UsersPage.class, Model.of("Users"))
            .setIconType(FontAwesomeIconType.user));
        return subMenu;
      }

    };
    navbar.addComponents(new ImmutableNavbarComponent(dropdown, Navbar.ComponentPosition.RIGHT));

    if (session.getUser().getFlags().contains("A")) {

      DropDownButton settingsDropDown = new NavbarDropDownButton(Model.of("Configurations")) {
        @Override
        protected List<AbstractLink> newSubMenuButtons(String buttonMarkupId) {
          final List<AbstractLink> subMenu = new ArrayList<AbstractLink>();
          subMenu.add(new MenuBookmarkablePageLink(SystemPropertiesPage.class, Model.of("System properties"))
              .setIconType(FontAwesomeIconType.question_circle));
          subMenu.add(new MenuBookmarkablePageLink(IrcServerConfigsPage.class, Model.of("Irc Server Configs"))
              .setIconType(FontAwesomeIconType.question_circle));
          subMenu.add(new MenuBookmarkablePageLink(NetworkConfigsPage.class, Model.of("Network Configs"))
              .setIconType(FontAwesomeIconType.question_circle));
          subMenu.add(new MenuBookmarkablePageLink(ChannelConfigsPage.class, Model.of("Channel Configs"))
              .setIconType(FontAwesomeIconType.question_circle));
          return subMenu;
        }
      };
      navbar.addComponents(new ImmutableNavbarComponent(settingsDropDown, Navbar.ComponentPosition.RIGHT));
    }


    NavbarAjaxLink navbarAjaxLink = new NavbarAjaxLink(Model.of("logout")) {
      @Override
      public void onClick(AjaxRequestTarget ajaxRequestTarget) {
        log.debug("Logging off!");
        session.logoffUser();
        getSession().invalidate();
        setResponsePage(HomePage.class);
      }
    };
    navbarAjaxLink.setIconType(GlyphIconType.logout);

    navbar.addComponents(NavbarComponents.transform(Navbar.ComponentPosition.RIGHT, navbarAjaxLink));
    return navbar;
  }
}
