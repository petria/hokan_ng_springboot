package org.freakz.hokan_ng_springboot.bot;

import org.apache.wicket.Session;
import org.apache.wicket.core.request.handler.IPageRequestHandler;
import org.apache.wicket.protocol.http.PageExpiredException;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Petri Airio on 29.12.2015.
 * -
 */
public class SessionExpiredListener extends AbstractRequestCycleListener {
  public void onRequestHandlerResolved(RequestCycle cycle, IRequestHandler handler) {
    if (handler instanceof IPageRequestHandler) {
      IPageRequestHandler pageHandler = (IPageRequestHandler) handler;

      HttpServletRequest request = (HttpServletRequest) cycle.getRequest().getContainerRequest();

      //check whether the requested session has expired
      boolean expired = request.getRequestedSessionId() != null && !request.isRequestedSessionIdValid();

      //check whether the requested page can be instantiated with the current session
      boolean authorized = Session.get().getAuthorizationStrategy().isInstantiationAuthorized(pageHandler.getPageClass());

      if (expired && !authorized) {
        throw new PageExpiredException("Session has expired!");
      }

    }
    super.onRequestHandlerResolved(cycle, handler);
  }
}