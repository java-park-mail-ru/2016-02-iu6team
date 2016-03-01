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
        users.put(userName, new UserProfile(userProfile));
        return true;
    }

    public long checkExists(UserProfile user) {
        if(users.containsKey(user.getLogin())){
            return users.get(user.getLogin()).getId();
        }
        return -1;
    }

    public UserProfile getUser(String userName) {
        return users.get(userName);
    }
}
