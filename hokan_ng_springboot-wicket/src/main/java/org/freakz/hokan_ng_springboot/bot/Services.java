package org.freakz.hokan_ng_springboot.bot;

import org.apache.wicket.Application;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.service.ChannelPropertyService;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.service.ChannelService;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.service.NetworkService;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.service.PropertyService;

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
}
