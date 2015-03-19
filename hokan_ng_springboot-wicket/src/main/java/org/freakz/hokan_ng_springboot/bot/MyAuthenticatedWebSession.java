package org.freakz.hokan_ng_springboot.bot;

/**
 * Created by Petri Airio on 19.3.2015.
 *
 */

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

/**
 * Authenticated session subclass. Note that it is derived from AuthenticatedWebSession which is
 * defined in the auth-role module.
 *
 * @author Jonathan Locke
 */
public class MyAuthenticatedWebSession extends AuthenticatedWebSession {
    /**
     * Construct.
     *
     * @param request The current request object
     */
    public MyAuthenticatedWebSession(Request request) {
        super(request);
    }

    /**
     * @see org.apache.wicket.authroles.authentication.AuthenticatedWebSession#authenticate(java.lang.String,
     * java.lang.String)
     */
    @Override
    public boolean authenticate(final String username, final String password) {
        final String WICKET = "wicket";

        // Check username and password
        return WICKET.equals(username) && WICKET.equals(password);
    }

    /**
     * @see org.apache.wicket.authroles.authentication.AuthenticatedWebSession#getRoles()
     */
    @Override
    public Roles getRoles() {
        if (isSignedIn()) {
            // If the user is signed in, they have these roles
            return new Roles(Roles.ADMIN);
        }
        return null;
    }
}
