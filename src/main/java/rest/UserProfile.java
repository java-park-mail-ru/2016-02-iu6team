package rest;

import org.jetbrains.annotations.NotNull;

/**
 * @author esin88
 */
public class UserProfile {
    @NotNull
    private long id;
    @NotNull
    private String login;
    @NotNull
    private String password;

    public UserProfile() {
        id = 0;
        login = "";
        password = "";
    }

    public UserProfile(@NotNull long id, @NotNull String login, @NotNull String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    @NotNull
    public long getId() {
        return id;
    }

    public void setId(@NotNull long id) {
        this.id = id;
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
