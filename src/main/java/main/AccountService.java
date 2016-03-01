package main;

import org.json.JSONObject;
import rest.UserProfile;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;


/**
 * @author esin88
 */
public class AccountService {
    private Map<String, UserProfile> users = new ConcurrentHashMap<>();
    private Map<String, UserProfile> sessions = new ConcurrentHashMap<>();

    public AccountService() {
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

    public UserProfile getUserById(long id) {
        for(Map.Entry<String, UserProfile> userTemp : users.entrySet()){
            if(userTemp.getValue().getId() == id){
                return userTemp.getValue();
            }
        }
        return null;
    }

    public void addSession(String sessionId, UserProfile user) {
        sessions.put(sessionId, user);
    }

    public boolean checkAuth(String sessionId) {
        return sessions.containsKey(sessionId);
    }

    public UserProfile giveProfileFromSessionId(String sessionId){
        return sessions.get(sessionId);
    }

    public String getIdByJson(long id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        return jsonObject.toString();
    }

    public boolean deleteSession(String sessionId){
        if(checkAuth(sessionId)){
            sessions.remove(sessionId);
            return true;
        }
        else {
            return false;
        }
    }
}
