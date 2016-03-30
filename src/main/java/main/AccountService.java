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

    UserDataSet getUser(long id);

    UserDataSet getUserByLogin(String login);
/*



    UserProfile getUserById(long id);

    void editUser(long id, UserProfile user, String sessionId);
*/
    boolean deleteSession(String sessionId);
    boolean isExists(@NotNull UserDataSet user);
    void addSession(String sessionId, UserDataSet user);
    boolean checkAuth(String sessionId);
    UserDataSet giveProfileFromSessionId(String sessionId);
    void deleteUser(long id);
    String getIdByJson(long id);
    String toJson(UserDataSet user);
    String toJsonError(String error);
}
