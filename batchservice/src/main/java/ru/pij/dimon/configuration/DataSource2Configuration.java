package ru.pij.dimon.configuration;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
public class DataSource2Configuration {

    @Autowired
    private Environment env;

    @Bean
    @Primary
    public DriverManagerDataSource ds2DataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource ();
        dataSource.setUrl(env.getProperty("spring.datasource.url.2"));
        dataSource.setUsername(env.getProperty("spring.datasource.username.2"));
        dataSource.setPassword(env.getProperty("spring.datasource.password.2"));
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("spring.datasource.driver-class-name.2")));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean ds2EntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(ds2DataSource());
        em.setPackagesToScan("ru.pij.dimon.pojo");
        em.setPersistenceUnitName("persistence_unit_2");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect.2"));
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto.2"));
        properties.put("hibernate.show-sql", env.getProperty("spring.jpa.show_sql.2"));
        em.setJpaPropertyMap(properties);
        em.afterPropertiesSet();
        return em;
    }

    @Bean
    @Primary
    public PlatformTransactionManager ds2TransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(ds2EntityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public SpringLiquibase liquibase2() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:read-changeLog.xml");
        liquibase.setDataSource(ds2DataSource());
        return liquibase;
    }
}
