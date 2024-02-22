package com.springboot.demos.config;

import java.util.HashMap;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "configDbEntityManagerFactory",
		transactionManagerRef = "configDbTransactionManager", basePackages = {"com.springboot.demos.dao"})
public class ConfigDataSourceConfig {

	  @Value("${spring.datasource.username}")
	  private String configDbUsername;

	  @Value("${spring.datasource.password}")
	  private String configDbPassword;

	  @Value("${spring.datasource.url}")
	  private String configDbJDBCConnectionString;

	  @Bean(name = "configDbDataSource")
	  @ConfigurationProperties(prefix = "spring.drugprice.datasource")
	  public DataSource dataSource() {

	      Properties prop = new Properties();
	        prop.put("spring.datasource.hikari.maximumPoolSize", 30);
	        prop.put("spring.datasource.hikari.idleTimeout", "30000");
	        prop.put("spring.datasource.hikari.poolName", "ClaimsHikariCP");
	        prop.put("spring.datasource.hikari.maxLifetime", "2000000");
	        prop.put("spring.datasource.hikari.connectionTimeout", "30000");
	   
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName("org.postgresql.Driver");
	    dataSource.setUrl(configDbJDBCConnectionString);
	    dataSource.setUsername(configDbUsername);
	    dataSource.setPassword(configDbPassword);
	    dataSource.setConnectionProperties(prop);
	    return dataSource;
	  }

	  @Bean(name = "configDbEntityManagerFactory")
	  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
	      EntityManagerFactoryBuilder builder, @Qualifier("configDbDataSource") DataSource dataSource) {
	    HashMap<String, Object> properties = new HashMap<>();

	    return builder.dataSource(dataSource).properties(properties)
	        .packages("com.springboot.demos.model").persistenceUnit("configDbBeans").build();
	  }

	  @Bean(name = "configDbTransactionManager")
	  public PlatformTransactionManager transactionManager(
	      @Qualifier("configDbEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
	    return new JpaTransactionManager(entityManagerFactory);
	  }
}
