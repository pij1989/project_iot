package ru.pij.dimon.configuration;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Objects;


@Configuration
@PropertySource("classpath:datasource-cfg.properties")

public class DataSource1Configuration {

    @Autowired
    private Environment env;

    @Bean
    public DriverManagerDataSource ds1DataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource ();
        dataSource.setUrl(env.getProperty("spring.datasource.url.1"));
        dataSource.setUsername(env.getProperty("spring.datasource.username.1"));
        dataSource.setPassword(env.getProperty("spring.datasource.password.1"));
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("spring.datasource.driver-class-name.1")));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean ds1EntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(ds1DataSource());
        em.setPackagesToScan("ru.pij.dimon.pojo");
        em.setPersistenceUnitName("persistence_unit_1");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect.1"));
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto.1"));
        properties.put("hibernate.show-sql", env.getProperty("spring.jpa.show_sql.1"));
        em.setJpaPropertyMap(properties);
        em.afterPropertiesSet();
        return em;
    }

    @Bean
    public PlatformTransactionManager ds1TransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(ds1EntityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public SpringLiquibase liquibase1(){
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:write-changeLog.xml");
        liquibase.setDataSource(ds1DataSource());
        return liquibase;
    }


}
