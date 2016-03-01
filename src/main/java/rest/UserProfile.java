package rest;

import org.jetbrains.annotations.NotNull;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author iu6team
 */
public class UserProfile {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);
    private long id;
    @NotNull
    private String login;
    @NotNull
    private String password;
    @NotNull
    private String email;

    public UserProfile() {
        id = ID_GENERATOR.getAndIncrement();
        login = "";
        password = "";
        email = "";
    }

    public UserProfile(@NotNull String login, @NotNull String password, @NotNull String email) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public UserProfile(UserProfile user) {
        id = ID_GENERATOR.getAndIncrement();
        login = user.login;
        password = user.password;
        email = user.email;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
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

    @NotNull
    public String getEmail() { return email; }

    public void setEmail(@NotNull String email) { this.email = email; }
}
