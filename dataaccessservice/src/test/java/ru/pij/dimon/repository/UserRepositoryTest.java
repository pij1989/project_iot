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
import ru.pij.dimon.helper.UserHelper;
import ru.pij.dimon.pojo.User;
import ru.pij.dimon.util.HibernateUtil;

import java.util.List;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class UserRepositoryTest extends DBTestCase {

    private User user;
    private SessionFactory sessionFactory;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserHelper userHelper;

    private static User createUser(int number){
        User user = new User();
        user.setFirstName("First name "+number);
        user.setLastName("Last name "+number);
        user.setLogin("login "+number);
        user.setEmail("email"+number+"@mail.ru");
        user.setPassword("password "+number);
        return user;
    }

    public UserRepositoryTest(String name) {
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
        return new FlatXmlDataSetBuilder().build(
                UserRepositoryTest.class.getClassLoader().getResourceAsStream("UserTest.xml"));
    }

    @Override
    protected DatabaseOperation getSetUpOperation() throws Exception {
        user = createUser(1);
        sessionFactory = HibernateUtil.getInstance().getTestSessionFactory();
        userRepository = new UserRepository(sessionFactory);
        roleRepository = new RoleRepository(sessionFactory);
        userHelper = new UserHelper();
        return super.getSetUpOperation();
    }


    @Test
    public void testSave() {

        Transaction tx = null;
        Session session = sessionFactory.getCurrentSession();
        try {
            tx = session.beginTransaction();
            userHelper.addRole(user,roleRepository.findById(1L));
            userRepository.save(user);
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testFindUserById() {
        Transaction tx = null;
        Session session = sessionFactory.getCurrentSession();
        try {
            tx = session.beginTransaction();
            User user = userRepository.findById(2L);
            assertNotNull(user);
            assertThat(2L, is(user.getId()));
            assertThat("Ivan",is(user.getFirstName()));
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testFindAll() {
        Transaction tx = null;
        Session session = sessionFactory.getCurrentSession();
        try {
            tx = session.beginTransaction();
            List<User> users = userRepository.findAll();
            assertNotNull(users);
            assertThat(3,is(users.size()));
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
            fail();
        }

    }

    @Test
    public void testUpdate() {
        Transaction tx = null;
        Session session = sessionFactory.getCurrentSession();
        try {
            tx = session.beginTransaction();
            User user = userRepository.findById(2L);
            assertNotNull(user);
            user.setFirstName("Artem");
            user.getRoles().add(roleRepository.findById(2L));
            userRepository.update(user);
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testDelete() {
        Transaction tx = null;
        Session session = sessionFactory.getCurrentSession();
        try {
            tx = session.beginTransaction();
            User user = userRepository.findById(4L);
            userRepository.delete(user);
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testFindUserByLogin(){
        Transaction tx = null;
        Session session = sessionFactory.getCurrentSession();
        try {
            tx = session.beginTransaction();
            User user = userRepository.findUserByLogin("petro");
            assertThat("petro",is(user.getLogin()));
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
            fail();
        }
    }

    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        user = null;
        userRepository = null;
        roleRepository = null;
        sessionFactory = null;
        return DatabaseOperation.DELETE_ALL;
//        return super.getTearDownOperation();
    }
}