package ru.pij.dimon.repository;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hamcrest.CoreMatchers;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import ru.pij.dimon.pojo.Sensor;
import ru.pij.dimon.util.HibernateUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class SensorRepositoryTest extends DBTestCase {

    private SessionFactory sessionFactory;
    private SensorRepository sensorRepository;

    public SensorRepositoryTest(String name) {
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
        sensorRepository = new SensorRepository(sessionFactory);
        return super.getSetUpOperation();
    }


    @Test
    public void testFindSensorsByType(){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List <Sensor> sensors = sensorRepository.findSensorsByType("temperature");
            assertNotNull(sensors);
            assertThat(sensors.get(0).getType(), CoreMatchers.is("temperature sensor"));
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
            fail();
        }
    }



    @Test
    public void testFindByDate() {
        Date fromDate = Timestamp.valueOf("2019-10-14 08:41:46");
        Date toDate = Timestamp.valueOf("2019-10-14 08:41:50");
        System.out.println(fromDate);
        System.out.println(toDate);
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Sensor> sensor = sensorRepository.findByDate(1L,fromDate,toDate);
            assertNotNull(sensor);
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
            fail();
        }
    }

    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        sensorRepository = null;
        sessionFactory = null;
//        return DatabaseOperation.DELETE_ALL;
        return super.getTearDownOperation();
    }
}