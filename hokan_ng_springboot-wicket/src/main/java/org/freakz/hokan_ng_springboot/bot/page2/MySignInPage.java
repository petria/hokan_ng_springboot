package org.freakz.hokan_ng_springboot.bot.page2;

import org.apache.wicket.authroles.authentication.panel.SignInPanel;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.freakz.hokan_ng_springboot.bot.jpa.service.UserService;

/**
 * Created by Petri Airio on 19.3.2015.
 *
 */
public final class MySignInPage extends WebPage {

    @SpringBean
    private UserService userService;

    public MySignInPage() {
        this(null);
    }

    /**
     * Constructor
     *
     * @param parameters The page parameters
     */
    public MySignInPage(final PageParameters parameters) {
        // That is all you need to add a logon panel to your application with rememberMe
        // functionality based on Cookies. Meaning username and password are persisted in a Cookie.
        // Please see ISecuritySettings#getAuthenticationStrategy() for details.
        add(new SignInPanel("signInPanel"));
    }
}

