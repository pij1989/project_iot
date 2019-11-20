package ru.pij.dimon.configuration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import ru.pij.dimon.helper.UserHelper;
import ru.pij.dimon.repository.*;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;


@Configuration
@ComponentScan("ru.pij.dimon")
@EnableWebMvc
@EnableTransactionManagement
@PropertySource("classpath:database.properties")
@PropertySource("classpath:hibernate.properties")
public class WebAppConfiguration  implements WebMvcConfigurer {

    @Autowired
    Environment env;

    @Bean
    public ViewResolver beanNameViewResolver() {
        return new BeanNameViewResolver();
    }

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Bean
    public BasicDataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(env.getProperty("connection.url"));
        dataSource.setUsername(env.getProperty("connection.username"));
        dataSource.setPassword(env.getProperty("connection.password"));
        dataSource.setDriverClassName(env.getProperty("connection.driver_class"));
        dataSource.setMaxTotal(Integer.parseInt(Objects.requireNonNull(env.getProperty("connection.max_total"))));
        dataSource.setInitialSize(10);
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(@Autowired DataSource dataSource) {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setPackagesToScan("ru.pij.dimon.pojo");
        sessionFactoryBean.setHibernateProperties(createHibernateProperties());
        return sessionFactoryBean;
    }

    private Properties createHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        return properties;
    }

    @Bean
    public HibernateTransactionManager transactionManager(@Autowired SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

    @Bean
    public UserRepository userRepository(@Autowired SessionFactory sessionFactory){
        return new UserRepository(sessionFactory);
    }

    @Bean
    public DeviceRepository deviceRepository(@Autowired SessionFactory sessionFactory){
        return new DeviceRepository(sessionFactory);
    }

    @Bean
    public SensorRepository sensorRepository(@Autowired SessionFactory sessionFactory){
        return new SensorRepository(sessionFactory);
    }

    @Bean
    public SensorValueRepository sensorValueRepository(@Autowired SessionFactory sessionFactory){
        return new SensorValueRepository(sessionFactory);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserHelper userHelper(){return new UserHelper();}

    @Bean
    public RoleRepository roleRepository(@Autowired SessionFactory sessionFactory){
        return new RoleRepository(sessionFactory);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
        registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/resources/js/");
    }
}
