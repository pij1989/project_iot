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
import ru.pij.dimon.pojo.Role;
import ru.pij.dimon.pojo.RoleName;
import ru.pij.dimon.util.HibernateUtil;

import java.util.Properties;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class RoleRepositoryTest extends DBTestCase {

    private SessionFactory sessionFactory;
    private RoleRepository roleRepository;


    public RoleRepositoryTest(String name) {
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
                .build(ClassLoader.getSystemClassLoader().getResourceAsStream("UserTest.xml"));
    }

    @Override
    protected DatabaseOperation getSetUpOperation() throws Exception {
        sessionFactory = HibernateUtil.getInstance().getTestSessionFactory();
        roleRepository = new RoleRepository(sessionFactory);
        return super.getSetUpOperation();
    }


    @Test()
    public void testFindRoleByName()  {
        Transaction tx = null;
        Session session = sessionFactory.getCurrentSession();
        try {
            tx = session.beginTransaction();
            Role role = roleRepository.findRoleByName(RoleName.ADMIN);
            assertThat("ADMIN", is(role.getRoleName().name()));
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
            fail();
        }
    }

    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        sessionFactory = null;
        roleRepository = null;
        return DatabaseOperation.DELETE_ALL;
//        return super.getTearDownOperation();
    }
}