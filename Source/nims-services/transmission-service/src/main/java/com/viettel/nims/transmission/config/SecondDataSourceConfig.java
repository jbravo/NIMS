package com.viettel.nims.transmission.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class SecondDataSourceConfig {

  @Bean(name = "secondDatasource")
  @ConfigurationProperties(prefix = "datasource.postgresql.second")
  public DataSource secondDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean(name = "secondJdbcTemplate")
  public JdbcTemplate jdbcTemplate(@Qualifier("secondDatasource") DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

//  @Bean
//  public LocalContainerEntityManagerFactoryBean secondEntityMangerFactory(
//      @Qualifier("secondDatasource") DataSource dataSource) {
//    LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
//    entityManagerFactory.setPersistenceUnitName("secondEntityManger");
//    entityManagerFactory.setDataSource(dataSource);
//    entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//    entityManagerFactory.setJpaDialect(new HibernateJpaDialect());
//    entityManagerFactory.setPackagesToScan("com.viettel.nims.transmission.postgre");
//    entityManagerFactory.setJpaProperties(additionalProperties());
//    return entityManagerFactory;
//  }

  @Bean(name = "secondTransactionManager")
  public PlatformTransactionManager transactionManager(@Qualifier("secondDatasource") DataSource dataSource) {
    DataSourceTransactionManager txManager = new DataSourceTransactionManager(dataSource);
    return txManager;
  }

//  private Properties additionalProperties() {
//    Properties properties = new Properties();
//    properties.setProperty("hibernate.dialect", "org.hibernate.spatial.dialect.postgis.PostgisDialect");
////    properties.setProperty("hibernate.ddl-auto", "none");
//    return properties;
//  }

}
