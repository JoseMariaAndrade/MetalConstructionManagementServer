package entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "USERS")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String password;
    @NotNull
    @Email
    private String emaill;
    @Version
    private int version;

    public User() {
    }

    public User(@NotNull String name, @NotNull String password, @NotNull @Email String emaill) {
        this.name = name;
        this.password = password;
        this.emaill = emaill;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmaill() {
        return emaill;
    }

    public void setEmaill(String emaill) {
        this.emaill = emaill;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
