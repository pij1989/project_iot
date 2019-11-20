package ru.pij.dimon.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {
    private SessionFactory testSessionFactory;

    private static volatile HibernateUtil hibernateUtil;

    private HibernateUtil(){
         testSessionFactory = new MetadataSources(
                new StandardServiceRegistryBuilder()
                        .configure("hibernate.cfg.test.xml")
                        .applySettings(getProperties("test-hibernate.properties"))
                        .build()
        ).buildMetadata().buildSessionFactory();
    }

    public static synchronized HibernateUtil getInstance(){
        if(hibernateUtil == null) hibernateUtil = new HibernateUtil();
        return hibernateUtil;
    }

    public SessionFactory getTestSessionFactory(){return testSessionFactory;}

    public Session getTestSession(){
        return testSessionFactory.openSession();
    }

    public Properties getProperties(String propertiesName){
        Properties properties = new Properties();
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(propertiesName);
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}