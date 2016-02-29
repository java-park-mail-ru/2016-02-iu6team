package main;

import rest.UserProfile;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;


/**
 * @author esin88
 */
public class AccountService {
    private Map<String, UserProfile> users = new ConcurrentHashMap<>();

    public AccountService() {
        //users.put("admin", new UserProfile("admin", "admin"));
        //users.put("guest", new UserProfile("guest", "12345"));
    }

    public Collection<UserProfile> getAllUsers() {
        return users.values();
    }

    public boolean addUser(String userName, UserProfile userProfile) {
        if (users.containsKey(userName))
            return false;
        users.put(userName, userProfile);
        return true;
    }

    public boolean checkExists(String userName) {
        return users.containsKey(userName);
    }

    public UserProfile getUser(String userName) {
        return users.get(userName);
    }
}
