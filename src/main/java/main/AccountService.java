package main;

import rest.UserProfile;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


/**
 * @author esin88
 */
public class AccountService {
    private Map<String, UserProfile> users = new ConcurrentHashMap<>();
    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);

    public AccountService() {
        users.put("admin", new UserProfile(ID_GENERATOR.getAndIncrement(),"admin", "admin"));
        users.put("guest", new UserProfile(ID_GENERATOR.getAndIncrement(),"guest", "12345"));
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

    public UserProfile getUser(String userName) {
        return users.get(userName);
    }
}
