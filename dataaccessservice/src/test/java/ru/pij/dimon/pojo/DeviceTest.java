package ru.pij.dimon.pojo;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import ru.pij.dimon.util.HibernateUtil;

import java.util.List;
import java.util.Properties;

public class DeviceTest extends DBTestCase {

    private Device device;

    public DeviceTest(String name) {
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
    protected DatabaseOperation getSetUpOperation() throws Exception {
        device = new Device();
        return super.getSetUpOperation();
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(
                DeviceTest.class.getClassLoader().getResourceAsStream("DeviceTest.xml")
        );
    }

    @Test
    public void testDevice(){
        Session session = HibernateUtil.getInstance().getTestSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            device = session.get(Device.class,1L);
            assertNotNull(device);
            assertEquals(3,device.getSensors().size());
            assertEquals("temperature sensor",device.getSensors().get(0).getType());
            assertEquals("illumination sensor",device.getSensors().get(1).getType());
            assertEquals("humidity sensor",device.getSensors().get(2).getType());
            tx.commit();
            session.close();

            session = HibernateUtil.getInstance().getTestSession();
            tx = session.beginTransaction();
            device.getLocation().setCity("London");
            session.merge(device);
            tx.commit();
            assertEquals("London",session.get(Device.class,1L).getLocation().getCity());
            session.close();

            session = HibernateUtil.getInstance().getTestSession();
            tx = session.beginTransaction();
            List<Device> devices = session.createQuery("from Device",Device.class).list();
            assertNotNull(devices);
            assertEquals(1,devices.size());
            tx.commit();
            session.close();

            session = HibernateUtil.getInstance().getTestSession();
            tx = session.beginTransaction();
            device = session.get(Device.class,1L);
            session.delete(device);
            tx.commit();
            session.close();

        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
            fail();
        } finally {
            session.close();
        }
    }

    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        device = null;
        return DatabaseOperation.DELETE;
//        return super.getTearDownOperation();
    }
}