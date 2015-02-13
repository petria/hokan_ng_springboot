package org.freakz.hokan_ng_sprintboot.engine;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.ejb.HibernatePersistence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

@Configuration
@EnableAutoConfiguration
//@EnableJpaRepositories("org.freakz.hokan_ng_sprintboot.common")
@ComponentScan({"org.freakz.hokan_ng_sprintboot.common", "org.freakz.hokan_ng_sprintboot.engine"})
//@EntityScan("org.freakz.hokan_ng_sprintboot.common")

public class HokanNgSpringBootEngine {

  private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
  private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
  private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
  private static final String PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";
  private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";

  @Resource private Environment environment;

  @Bean
  public DataSource dataSource() {
    MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
    //    BoneCPDataSource dataSource = new BoneCPDataSource();
    dataSource.setUser("hokan_ng");
    dataSource.setPassword("hokan_ng");
    dataSource.setDatabaseName("hokan_ng_springboot");
    dataSource.setServerName("localhost");
    dataSource.setPort(3306);
    return dataSource;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() throws ClassNotFoundException {
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setDataSource(dataSource());
    entityManagerFactoryBean.setPackagesToScan("org.freakz.hokan_ng_sprintboot.common.jpa.entity");
    entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);
    Properties jpaProterties = new Properties();
    jpaProterties.put(PROPERTY_NAME_HIBERNATE_DIALECT, "org.hibernate.dialect.MySQL5InnoDBDialect");
    jpaProterties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, "true");
    jpaProterties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, "update");
    jpaProterties.put(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY, "org.hibernate.cfg.ImprovedNamingStrategy");
    jpaProterties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, "true");
    entityManagerFactoryBean.setJpaProperties(jpaProterties);
    return entityManagerFactoryBean;
  }

  public static void main(String[] args) {
    SpringApplication.run(HokanNgSpringBootEngine.class, args);
  }

}
