package org.freakz.hokan_ng_springboot.bot;

import org.apache.wicket.Application;
import org.freakz.hokan_ng_springboot.bot.jpa.service.*;
import org.freakz.hokan_ng_springboot.bot.wicketservices.HokanStatusService;

/**
 * Created by Petri Airio on 24.3.2015.
 *
 */
public class Services {

  public static ChannelService getChannelService() {
    HokanNgWicketApplication application = (HokanNgWicketApplication) Application.get();
    return application.getChannelService();
  }

  public static ChannelPropertyService getChannelPropertyService() {
    HokanNgWicketApplication application = (HokanNgWicketApplication) Application.get();
    return application.getChannelPropertyService();
  }

  public static NetworkService getNetworkService() {
    HokanNgWicketApplication application = (HokanNgWicketApplication) Application.get();
    return application.getNetworkService();
  }

  public static PropertyService getPropertyService() {
    HokanNgWicketApplication application = (HokanNgWicketApplication) Application.get();
    return application.getPropertyService();
  }

  public static UserService getUserService() {
    HokanNgWicketApplication application = (HokanNgWicketApplication) Application.get();
    return application.getUserService();
  }

  public static IrcServerConfigService getIrcServerConfigService() {
    HokanNgWicketApplication application = (HokanNgWicketApplication) Application.get();
    return application.getIrcServerConfigService();
  }

  public static HokanStatusService getHokanStatusService() {
    HokanNgWicketApplication application = (HokanNgWicketApplication) Application.get();
    return application.getHokanStatusService();

  }

}
