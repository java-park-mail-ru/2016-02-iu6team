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
        try ( Session session = sessionFactory.openSession() ) {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            if (dao.getUserByLogin(userProfile.getLogin()) != null || dao.getUserByEmail(userProfile.getEmail()) != null) {
                return false;
            } else {
                dao.addUser(userProfile);
                return true;
            }
        }
    }
/*
    @Override
    public boolean addUser(String userName, UserProfile userProfile) {
        if (users.containsKey(userName))
            return false;
        users.put(userName, new UserProfile(userProfile));
        usersId.put(users.get(userProfile.getLogin()).getId(), users.get(userProfile.getLogin()));
        return true;
    }

    @Override
    public boolean isExists(@NotNull UserProfile user) {
        return users.containsKey(user.getLogin());
    }

    @Override
    public UserProfile getUser(String userName) {
        return users.get(userName);
    }

    @Override
    public void addSession(String sessionId, UserProfile user) {
        sessions.put(sessionId, users.get(user.getLogin()));
    }
    @Override
    public boolean checkAuth(String sessionId) {
        return sessions.containsKey(sessionId);
    }
    @Override
    public UserProfile giveProfileFromSessionId(String sessionId){
        return sessions.get(sessionId);
    }

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
    public UserProfile getUserById(long id) {
        return usersId.get(id);
    }
    @Override
    public void editUser(long id, UserProfile user, String sessionId){
        user.setId(usersId.get(id).getId());
        users.put(user.getLogin(), user);
        users.remove(usersId.get(id).getLogin());
        sessions.replace(sessionId, user);
        usersId.replace(id, user);
    }
    @Override
    public void deleteUser(long id) {
        users.remove(usersId.get(id).getLogin());
        usersId.remove(id);
    }
    */
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
