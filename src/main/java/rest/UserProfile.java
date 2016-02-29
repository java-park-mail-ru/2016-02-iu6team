package rest;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author esin88
 */
public class UserProfile {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);
    @NotNull
    private long id;
    @NotNull
    private String login;
    @NotNull
    private String password;

    public UserProfile() {
        id = ID_GENERATOR.getAndIncrement();
        login = "";
        password = "";
    }

    public UserProfile(@NotNull String login, @NotNull String password) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.login = login;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdByJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        return jsonObject.toString();
    }

    @NotNull
    public String getLogin() {
        return login;
    }

    public void setLogin(@NotNull String login) {
        this.login = login;
    }

    @NotNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NotNull String password) {
        this.password = password;
    }
}
