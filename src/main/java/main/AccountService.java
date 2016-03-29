package main;

import org.jetbrains.annotations.NotNull;
import db.UserDataSet;

import java.util.Collection;
import java.util.List;

/**
 * Created by qwerty on 28.03.16.
 */
public interface AccountService {

    List<UserDataSet> getAllUsers();

    boolean addUser(UserDataSet userProfile);
/*
    UserProfile getUser(String userName);

    boolean isExists(@NotNull UserProfile user);

    void addSession(String sessionId, UserProfile user);

    boolean checkAuth(String sessionId);

    UserProfile giveProfileFromSessionId(String sessionId);



    boolean deleteSession(String sessionId);

    UserProfile getUserById(long id);

    void editUser(long id, UserProfile user, String sessionId);

    void deleteUser(long id);
*/

    String getIdByJson(long id);
    String toJson(UserDataSet user);
    String toJsonError(String error);
}
