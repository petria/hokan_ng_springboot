package org.freakz.hokan_ng_springboot.bot;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.crypt.CharEncoding;
import org.freakz.hokan_ng_springboot.bot.page.FooPage;
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

//@SpringBootApplication
public class HokanNgWicketApplication extends WebApplication {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public Class<? extends WebPage> getHomePage() {
        return FooPage.class;
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
        mountPage("/FooPage", FooPage.class);
    }


}
