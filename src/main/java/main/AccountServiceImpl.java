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
public class AccountServiceImpl implements AccountService {
    private Map<String, UserProfile> users = new ConcurrentHashMap<>();
    private Map<String, UserProfile> sessions = new ConcurrentHashMap<>();
    private Map<Long, UserProfile> usersId = new ConcurrentHashMap<>();

    @Override
    public Collection<UserProfile> getAllUsers() {
        return users.values();
    }

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
    public String getIdByJson(long id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        return jsonObject.toString();
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
    @Override
    public String toJson(UserProfile user) {
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
}
