package org.freakz.hokan_ng_springboot.bot;

import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.crypt.CharEncoding;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.service.NetworkService;
import org.freakz.hokan_ng_springboot.bot.page.HokanBasePage;
import org.freakz.hokan_ng_springboot.bot.page2.MySignInPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*

 */
@Configuration
@ComponentScan({"org.freakz.hokan_ng_springboot.bot"})
@EnableJpaRepositories({"org.freakz.hokan_ng_springboot.bot"})
@EnableAutoConfiguration
@EnableTransactionManagement
public class HokanNgWicketApplication extends AuthenticatedWebApplication {

  @Autowired
  private NetworkService networkService;

  @Autowired
  private ApplicationContext applicationContext;

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
//        mountPage("/FooPage", FooPage.class);
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

}
