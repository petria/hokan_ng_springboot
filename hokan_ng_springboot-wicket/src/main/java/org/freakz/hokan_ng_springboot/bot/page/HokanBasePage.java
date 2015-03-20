package org.freakz.hokan_ng_springboot.bot.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.freakz.hokan_ng_springboot.bot.panel.HeaderPanel;
import org.freakz.hokan_ng_springboot.bot.panel.MainContentPanel;
import org.freakz.hokan_ng_springboot.bot.panel.NavigationPanel;

/**
 * Created by Petri Airio on 20.3.2015.
 *
 */
public class HokanBasePage extends WebPage {

    private static final long serialVersionUID = 1L;

    public HokanBasePage() {
    }

    public HokanBasePage(IModel<?> model) {
        super(model);
    }

    public HokanBasePage(final PageParameters parameters) {
        super(parameters);
        HeaderPanel headerPanel = new HeaderPanel("headerPanel", this);
        NavigationPanel navigationPanel = new NavigationPanel("navigationPanel", this);
        MainContentPanel mainContentPanel = new MainContentPanel("mainContentPanel", this);
        add(headerPanel);
        add(navigationPanel);
        add(mainContentPanel);
    }



}
