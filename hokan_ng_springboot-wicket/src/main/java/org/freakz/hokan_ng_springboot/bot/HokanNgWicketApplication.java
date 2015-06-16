package org.freakz.hokan_ng_springboot.bot;

import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.crypt.CharEncoding;
import org.freakz.hokan_ng_springboot.bot.jpa.service.*;
import org.freakz.hokan_ng_springboot.bot.page.HokanBasePage;
import org.freakz.hokan_ng_springboot.bot.page2.MySignInPage;
import org.freakz.hokan_ng_springboot.bot.service.HokanStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*

 */
@Configuration
@ComponentScan({"org.freakz.hokan_ng_springboot.bot"})
@EnableJpaRepositories({"org.freakz.hokan_ng_springboot.bot"})
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableScheduling
public class HokanNgWicketApplication extends AuthenticatedWebApplication {

  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private ChannelService channelService;

  @Autowired
  private ChannelPropertyService channelPropertyService;

  @Autowired
  private IrcServerConfigService ircServerConfigService;

  @Autowired
  private NetworkService networkService;

  @Autowired
  private PropertyService propertyService;

  @Autowired
  private HokanStatusService statusService;

  @Autowired
  private UrlLoggerService urlLoggerService;

  @Autowired
  private UserService userService;



  @Override
  public Class<? extends WebPage> getHomePage() {
    return HokanBasePage.class;
  }

  public static void main(String[] args) {
    SpringApplication.run(HokanNgWicketApplication.class, args);
  }

  @Override
  public void init() {
    super.init();
    getRequestCycleSettings().setResponseRequestEncoding(CharEncoding.UTF_8);
    getMarkupSettings().setDefaultMarkupEncoding(CharEncoding.UTF_8);
    getComponentInstantiationListeners().add(new SpringComponentInjector(this, applicationContext));
/*    SecurePackageResourceGuard guard = new SecurePackageResourceGuard() {
      @Override
      public boolean accept(Class<?> scope, String absolutePath) {
        return true;
//        return super.accept(scope, absolutePath);
      }
    };
    guard.addPattern("+*.png");
    guard.setAllowAccessToRootResources(true);
    getResourceSettings().setPackageResourceGuard(guard);*/
  }

  @Override
  protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
    return MyAuthenticatedWebSession.class;
  }

  @Override
  protected Class<? extends WebPage> getSignInPageClass() {
    return MySignInPage.class;
  }

  public NetworkService getNetworkService() {
    return networkService;
  }

  public ChannelService getChannelService() {
    return channelService;
  }

  public ChannelPropertyService getChannelPropertyService() {
    return channelPropertyService;
  }

  public PropertyService getPropertyService() {
    return propertyService;
  }

  public UserService getUserService() {
    return userService;
  }

  public IrcServerConfigService getIrcServerConfigService() {
    return ircServerConfigService;
  }

  public HokanStatusService getHokanStatusService() {
    return statusService;
  }

  public UrlLoggerService getUrlLoggerService() {
    return urlLoggerService;
  }
}
