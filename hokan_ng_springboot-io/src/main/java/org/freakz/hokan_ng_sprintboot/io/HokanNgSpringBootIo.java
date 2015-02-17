package org.freakz.hokan_ng_sprintboot.io;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableAutoConfiguration
@ComponentScan({ "org.freakz.hokan_ng_sprintboot.common", "org.freakz.hokan_ng_sprintboot.io" })
@Slf4j
public class HokanNgSpringBootIo {

  @Value("${datasource.driverclassname}") private String datasource_driverclassname;
  @Value("${datasource.url}") private String datasource_url;
  @Value("${datasource.username}") private String datasource_username;
  @Value("${datasource.password}") private String datasource_password;

  @Value("${hibernate.hbm2ddl.auto}") private String hibernate_hbm2ddl_auto;
  @Value("${hibernate.ejb.naming_strategy}") private String hibernate_ejb_naming_strategy;
  @Value("${hibernate.dialect}") private String hibernate_dialect;
  @Value("${hibernate.format_sql}") private String hibernate_format_sql;
  @Value("${hibernate.show_sql}") private String hibernate_show_sql;

  @Value("${entitymanager.packages.to.scan}") private String entitymanager_packages_to_scan;

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(datasource_driverclassname);
    dataSource.setUrl(datasource_url);
    dataSource.setUsername(datasource_username);
    dataSource.setPassword(datasource_password);
    return dataSource;
  }

  Properties additionalProperties() {
    Properties properties = new Properties();
    properties.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
    properties.setProperty("hibernate.ejb.naming_strategy", hibernate_ejb_naming_strategy);
    properties.setProperty("hibernate.dialect", hibernate_dialect);
    properties.setProperty("hibernate.format_sql", hibernate_format_sql);
    properties.setProperty("hibernate.show_sql", hibernate_show_sql);
    return properties;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() throws ClassNotFoundException {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource());
    em.setPackagesToScan(entitymanager_packages_to_scan);

    JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);
    em.setJpaProperties(additionalProperties());
    em.afterPropertiesSet();

    return em;
  }

  public static void main(String[] args) {
    SpringApplication.run(HokanNgSpringBootIo.class, args);
  }

}
