package main;

import org.jetbrains.annotations.NotNull;
import rest.UserProfile;

import java.util.Collection;

/**
 * Created by qwerty on 28.03.16.
 */
public interface AccountService {

    Collection<UserProfile> getAllUsers();

    boolean addUser(String userName, UserProfile userProfile);

    UserProfile getUser(String userName);

    boolean isExists(@NotNull UserProfile user);

    void addSession(String sessionId, UserProfile user);

    boolean checkAuth(String sessionId);

    UserProfile giveProfileFromSessionId(String sessionId);

    String getIdByJson(long id);

    boolean deleteSession(String sessionId);

    UserProfile getUserById(long id);

    void editUser(long id, UserProfile user, String sessionId);

    void deleteUser(long id);

    String toJson(UserProfile user);

    String toJsonError(String error);
}
