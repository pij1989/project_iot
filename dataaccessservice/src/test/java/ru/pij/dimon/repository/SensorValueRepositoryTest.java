package ru.pij.dimon.repository;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import ru.pij.dimon.pojo.SensorValue;
import ru.pij.dimon.util.HibernateUtil;

import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class SensorValueRepositoryTest extends DBTestCase {
    private SessionFactory sessionFactory;
    private SensorValueRepository sensorValueRepository;

    public SensorValueRepositoryTest(String name) {
        super(name);
        Properties properties = HibernateUtil.getInstance().getProperties("test-database.properties");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS,
                properties.getProperty("driver"));
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,
                properties.getProperty("url"));
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME,
                properties.getProperty("username"));
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD,
                properties.getProperty("password"));
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder()
                .build(ClassLoader.getSystemClassLoader().getResourceAsStream("DeviceTest.xml"));
    }

    @Override
    protected DatabaseOperation getSetUpOperation() throws Exception {
        sessionFactory = HibernateUtil.getInstance().getTestSessionFactory();
        sensorValueRepository = new SensorValueRepository(sessionFactory);
        return super.getSetUpOperation();
    }


    @Test
    public void testFindByDateInterval()  {
        Transaction tx = null;
        Session session = sessionFactory.getCurrentSession();
        try {
            tx = session.beginTransaction();
            List<SensorValue> sensorValues = sensorValueRepository.findByDateInterval(1L,Timestamp.valueOf("2019-10-14 08:41:46"),Timestamp.valueOf("2019-10-14 08:41:47"));
            assertNotNull(sensorValues);
            assertThat(sensorValues.size(), is(2));
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
            fail();
        }
    }

    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        sensorValueRepository = null;
        sessionFactory = null;
        return DatabaseOperation.DELETE_ALL;
    }
}