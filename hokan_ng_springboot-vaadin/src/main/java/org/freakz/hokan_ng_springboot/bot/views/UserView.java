/*
 * Copyright 2015 The original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.freakz.hokan_ng_springboot.bot.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import org.freakz.hokan_ng_springboot.bot.Sections;
import org.freakz.hokan_ng_springboot.bot.backend.MyBackend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;

/**
 * View that is available for all users.
 *
 * @author Petter Holmström (petter@vaadin.com)
 */
@Secured({"ROLE_USER", "ROLE_ADMIN"})
@SpringView(name = "user")
@SideBarItem(sectionId = Sections.VIEWS, caption = "PircBotUser View")
@FontAwesomeIcon(FontAwesome.ARCHIVE)
public class UserView extends CustomComponent implements View {

    private final MyBackend backend;

    @Autowired
    public UserView(MyBackend backend) {
        this.backend = backend;
        Button button = new Button("Call user backend", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Notification.show(UserView.this.backend.echo("Hello PircBotUser World!"));
            }
        });
        setCompositionRoot(button);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
