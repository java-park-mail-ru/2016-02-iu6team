package main;

import db.UserDataSet;
import db.UserDataSetDAO;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * @author iu6team
 */
public class AccountServiceImpl implements AccountService {
    private Map<String, UserDataSet> sessions = new ConcurrentHashMap<>();
    private SessionFactory sessionFactory;

    public AccountServiceImpl() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/javaDB");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "1");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");

        sessionFactory = createSessionFactory(configuration);
    }

    @Override
    public List<UserDataSet> getAllUsers() {
        Session session = sessionFactory.openSession();
        UserDataSetDAO dao = new UserDataSetDAO(session);
        return dao.getAllUsers();
    }

    @Override
    public boolean addUser(UserDataSet userProfile) {
        Session session = sessionFactory.openSession();
        UserDataSetDAO dao = new UserDataSetDAO(session);
        if (dao.getUserByLogin(userProfile.getLogin()) != null || dao.getUserByEmail(userProfile.getEmail()) != null) {
            return false;
        } else {
            dao.addUser(userProfile);
            return true;
        }
    }

    @Override
    public UserDataSet getUser(long id) {
        Session session = sessionFactory.openSession();
        UserDataSetDAO dao = new UserDataSetDAO(session);
        return dao.getUser(id);
    }

    @Override
    public UserDataSet getUserByLogin(String login) {
        Session session = sessionFactory.openSession();
        UserDataSetDAO dao = new UserDataSetDAO(session);
        return dao.getUserByLogin(login);
    }
/*


    @Override
    public void editUser(long id, UserProfile user, String sessionId){
        user.setId(usersId.get(id).getId());
        users.put(user.getLogin(), user);
        users.remove(usersId.get(id).getLogin());
        sessions.replace(sessionId, user);
        usersId.replace(id, user);
    }
    */

    @Override
    public boolean deleteSession(String sessionId){
        if(checkAuth(sessionId)){
            sessions.remove(sessionId);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean isExists(@NotNull UserDataSet user) {
        Session session = sessionFactory.openSession();
        UserDataSetDAO dao = new UserDataSetDAO(session);
        return (dao.getUserByLogin(user.getLogin()) != null);
    }

    @Override
    public void addSession(String sessionId, UserDataSet user) {
        sessions.put(sessionId, user);
    }
    @Override
    public boolean checkAuth(String sessionId) {
        return sessions.containsKey(sessionId);
    }
    @Override
    public UserDataSet giveProfileFromSessionId(String sessionId){
        return sessions.get(sessionId);
    }

    @Override
    public void deleteUser(long id) {
        Session session = sessionFactory.openSession();
        UserDataSetDAO dao = new UserDataSetDAO(session);
        dao.deleteUser(id);
    }

    @Override
    public String getIdByJson(long id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        return jsonObject.toString();
    }

    @Override
    public String toJson(UserDataSet user) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", user.getId());
        jsonObject.put("login", user.getLogin());
        jsonObject.put("email", user.getEmail());
        return jsonObject.toString();
    }
    @Override
    public String toJsonError(String error) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error:", error);
        return jsonObject.toString();
    }


    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
