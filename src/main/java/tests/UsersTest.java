package tests;

import db.UserDataSet;
import db.UserDataSetDAO;
import main.AccountService;
import main.AccountServiceImpl;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import rest.Session;
import rest.Users;
import main.Context;

import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;

/**
 * Created by iu6team on 30.03.16.
 */
@FixMethodOrder(MethodSorters.JVM)
public class UsersTest extends JerseyTest {
    private SessionFactory sessionFactory;

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    protected Application configure() {
        final Context context = new Context();
        context.put(AccountService.class, new AccountServiceImpl());

        final ResourceConfig config = new ResourceConfig(Users.class, Session.class);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpSession session = mock(HttpSession.class);

        config.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(context);
                bind(request).to(HttpServletRequest.class);
                bind(session).to(HttpSession.class);
                when(request.getSession()).thenReturn(session);
                when(session.getId()).thenReturn("session");
            }
        });

        return config;
    }

    @Before
    public void setUpChild() throws Exception {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/javaDB");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "1");
       // configuration.setProperty("hibernate.connection.password", "mysql");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");

        sessionFactory = createSessionFactory(configuration);

        org.hibernate.Session session = sessionFactory.openSession();
        UserDataSetDAO dao = new UserDataSetDAO(session);

        UserDataSet user1 = new UserDataSet(),
                    user2 = new UserDataSet();

        user1.setLogin("user1");
        user1.setEmail("user1@mail.ru");
        user1.setPassword("12345");

        user2.setLogin("user2");
        user2.setEmail("user2@mail.ru");
        user2.setPassword("54321");

        dao.addUser(user1);
        dao.addUser(user2);

        target("session").request().put(Entity.json(user1));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        final String actualJSON = target("user").request().get(String.class),
                     expectedJSON = "[{\"email\":\"user1@mail.ru\",\"login\":\"user1\",\"password\":\"12345\"}," +
                                     "{\"email\":\"user2@mail.ru\",\"login\":\"user2\",\"password\":\"54321\"}]";

        assertEquals(expectedJSON, actualJSON);
    }

    @Test
    public void testGetUserById() throws Exception {

    }

    @Test
    public void testCreateUser() throws Exception {

    }

    @Test
    public void testEditUser() throws Exception {

    }

    @Test
    public void testDeleteUser() throws Exception {

    }
}