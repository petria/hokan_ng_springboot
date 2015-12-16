package org.freakz.hokan_ng_springboot.bot;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import de.agilecoders.wicket.core.settings.IBootstrapSettings;
import de.agilecoders.wicket.core.settings.ThemeProvider;
import de.agilecoders.wicket.less.BootstrapLess;
import de.agilecoders.wicket.themes.markup.html.bootswatch.BootswatchTheme;
import de.agilecoders.wicket.themes.markup.html.bootswatch.BootswatchThemeProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.UrlResourceReference;
import org.apache.wicket.resource.JQueryResourceReference;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.crypt.CharEncoding;
import org.freakz.hokan_ng_springboot.bot.enums.CommandLineArgs;
import org.freakz.hokan_ng_springboot.bot.jpa.service.*;
import org.freakz.hokan_ng_springboot.bot.pages.HokanSignInPage;
import org.freakz.hokan_ng_springboot.bot.pages.HomePage;
import org.freakz.hokan_ng_springboot.bot.service.HokanStatusService;
import org.freakz.hokan_ng_springboot.bot.util.CommandLineArgsParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.jms.ConnectionFactory;
import java.util.Map;

/*

 */
@Configuration
@ComponentScan({"org.freakz.hokan_ng_springboot.bot"})
@EnableJpaRepositories({"org.freakz.hokan_ng_springboot.bot"})
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableScheduling
@Slf4j
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

//  @Autowired
//  private HokanStatusService statusService;

  @Autowired
  private UrlLoggerService urlLoggerService;

  @Autowired
  private UserService userService;

  private static String JMS_BROKER_URL = "tcp://localhost:61616";

  @Bean
  public ConnectionFactory connectionFactory() {
    return new ActiveMQConnectionFactory(JMS_BROKER_URL);
  }


  @Override
  public Class<? extends WebPage> getHomePage() {
    return HomePage.class;
  }

  public static void main(String[] args) {
    CommandLineArgsParser parser = new CommandLineArgsParser(args);
    Map<CommandLineArgs, String> parsed = parser.parseArgs();
    String url = parsed.get(CommandLineArgs.JMS_BROKER_URL);
    if (url != null) {
      JMS_BROKER_URL = url;
    }
    log.debug("JMS_BROKER_URL: {}", JMS_BROKER_URL);

    SpringApplication.run(HokanNgWicketApplication.class, args);
  }

  @Override
  public void init() {
    super.init();
    getRequestCycleSettings().setResponseRequestEncoding(CharEncoding.UTF_8);
    getMarkupSettings().setDefaultMarkupEncoding(CharEncoding.UTF_8);
    getComponentInstantiationListeners().add(new SpringComponentInjector(this, applicationContext));
    configureBootstrap();
    mountPage("/login", HokanSignInPage.class);

    addResourceReplacement(JQueryResourceReference.get(),
        new UrlResourceReference(
            Url.parse("http://code.jquery.com/jquery-1.11.0.min.js")));

  }

  private void configureBootstrap() {

    final IBootstrapSettings settings = new BootstrapSettings();
    settings.useCdnResources(true);
    final ThemeProvider themeProvider = new BootswatchThemeProvider(BootswatchTheme.Spacelab);
    settings.setThemeProvider(themeProvider);

    Bootstrap.install(this, settings);
    BootstrapLess.install(this);
  }

  @Override
  protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
    return HokanAuthenticatedWebSession.class;
  }

  @Override
  protected Class<? extends WebPage> getSignInPageClass() {
    return HokanSignInPage.class;
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
    return null;
//    return statusService;
  }

  public UrlLoggerService getUrlLoggerService() {
    return urlLoggerService;
  }
}
