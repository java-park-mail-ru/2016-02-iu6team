package db;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;

/**
 * author iu6team
 */
@Entity
@Table(name = "Users")
public class UserDataSet implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "login")
    private String login;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

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

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    @NotNull
    public String getEmail() { return email; }

    public void setEmail(@NotNull String email) { this.email = email; }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "id=" + id +
                ", name='" + login + '\'' +
                "email=" + email + '\'' +
                '}';
    }
}