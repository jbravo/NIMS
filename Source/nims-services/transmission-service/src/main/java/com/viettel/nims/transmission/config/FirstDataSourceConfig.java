package com.viettel.nims.transmission.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
public class FirstDataSourceConfig {

  @Primary
  @Bean(name = "firstDatasource")
  @ConfigurationProperties(prefix = "datasource.maria.primary")
  public DataSource firstDatasource() {
    return DataSourceBuilder.create().build();
  }


  @Bean
  public LocalContainerEntityManagerFactoryBean firstEntityMangerFactory(
      @Qualifier("firstDatasource") DataSource dataSource) {
    LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactory.setPersistenceUnitName("firstEntityManger");
    entityManagerFactory.setDataSource(dataSource);
    entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    entityManagerFactory.setJpaDialect(new HibernateJpaDialect());
    entityManagerFactory.setPackagesToScan("com.viettel.nims.transmission");
    entityManagerFactory.setJpaProperties(additionalProperties());
    return entityManagerFactory;
  }

  private Properties additionalProperties() {
    Properties properties = new Properties();
    properties.setProperty("hibernate.dialect", "org.hibernate.spatial.dialect.mysql.MySQL5InnoDBSpatialDialect");
//    properties.setProperty("hibernate.ddl-auto", "none");
    return properties;
  }

  @Bean(name = "transactionManager")
  public PlatformTransactionManager transactionManager(@Qualifier("firstDatasource") DataSource dataSource) {
    DataSourceTransactionManager txManager = new DataSourceTransactionManager(dataSource);
    return txManager;
  }

}
