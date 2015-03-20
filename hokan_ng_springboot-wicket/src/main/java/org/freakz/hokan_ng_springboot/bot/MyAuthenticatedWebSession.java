package org.freakz.hokan_ng_springboot.bot;

/**
 * Created by Petri Airio on 19.3.2015.
 *
 */

import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.User;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
public class MyAuthenticatedWebSession extends AuthenticatedWebSession {

    @SpringBean
    private UserService userService;

    public MyAuthenticatedWebSession(Request request) {
        super(request);
        Injector.get().inject(this);
    }

    @Override
    public boolean authenticate(final String username, final String password) {
//        final String WICKET = "wicket";
        // Check username and password
//        return WICKET.equals(username) && WICKET.equals(password);
        return  checkAccess(username, password);
    }

    private boolean checkAccess(final String username, final String password) {
        List<User> userList = userService.findAll();
        for  (User user : userList) {
            if (user.getNick().equalsIgnoreCase(username)) {
                log.info("User exists");
                if (user.getPassword().equals(password)) {
                    log.info("User authorized: {}", username);
                    return true;
                } else {
                    log.info("User invalid password: {}", username);
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public Roles getRoles() {
        if (isSignedIn()) {
            // If the user is signed in, they have these roles
            return new Roles(Roles.ADMIN);
        }
        return null;
    }
}
