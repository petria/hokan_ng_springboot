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

package org.freakz.hokan_ng_springboot.bot.pages;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.freakz.hokan_ng_springboot.bot.panel.liveirc.LiveIrcChannelPanel;
import org.freakz.hokan_ng_springboot.bot.panel.status.HokanStatusPanel;

@AuthorizeInstantiation("USER")
public class HomePage extends BasePage {

	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);
		add(new LiveIrcChannelPanel("liveIrcChannelPanel"));
//    add(new HokanStatusPanel("statusPanel"));
//		add(new Label("version", getApplication().getFrameworkSettings().getVersion()));

		// TODO Add your page's components here

    }
}
