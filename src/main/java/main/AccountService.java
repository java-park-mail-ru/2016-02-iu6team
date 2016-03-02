package main;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import rest.UserProfile;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;


/**
 * @author iu6team
 */
public class AccountService {
    private Map<String, UserProfile> users = new ConcurrentHashMap<>();
    private Map<String, UserProfile> sessions = new ConcurrentHashMap<>();
    private Map<Long, UserProfile> usersId = new ConcurrentHashMap<>();

    public Collection<UserProfile> getAllUsers() {
        return users.values();
    }

    public boolean addUser(String userName, UserProfile userProfile) {
        if (users.containsKey(userName))
            return false;
        users.put(userName, new UserProfile(userProfile));
        usersId.put(users.get(userProfile.getLogin()).getId(), users.get(userProfile.getLogin()));
        return true;
    }

    public boolean isExists(@NotNull UserProfile user) {
        return users.containsKey(user.getLogin());
    }


    public UserProfile getUser(String userName) {
        return users.get(userName);
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

    public UserProfile getUserById(long id) {
        return usersId.get(id);
    }

    public void editUser(long id, UserProfile user, String sessionId){
        user.setId(usersId.get(id).getId());
        users.put(user.getLogin(), user);
        users.remove(usersId.get(id).getLogin());
        sessions.replace(sessionId, user);
        usersId.replace(id, user);
    }

    public void deleteUser(long id) {
        users.remove(usersId.get(id).getLogin());
        usersId.remove(id);
    }

    public String toJson(UserProfile user) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", user.getId());
        jsonObject.put("login", user.getLogin());
        jsonObject.put("email", user.getEmail());
        return jsonObject.toString();
    }

    public String toJsonError(String error) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error:", error);
        return jsonObject.toString();
    }
}
