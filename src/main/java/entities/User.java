package entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Entity
@Table(name = "USERS")
@NamedQueries({
        @NamedQuery(name = "getUserByEmail", query = "SELECT u FROM User u WHERE u.email=:email")
})
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
    private String email;
    @Version
    private int version;

    public User() {
    }

    public User(@NotNull String name, @NotNull String password, @NotNull @Email String email) {
        this.name = name;
        this.password = hashPassword(password);
        this.email = email;
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

    public static String hashPassword(String password) {
        char[] encoded = null;
        try {
            ByteBuffer passwordBuffer = Charset.defaultCharset().encode(CharBuffer.wrap(password));
            byte[] passwordBytes = passwordBuffer.array();
            MessageDigest messageDigestEnconde = MessageDigest.getInstance("SHA-256");
            messageDigestEnconde.update(passwordBytes, 0, password.toCharArray().length);
            encoded = new BigInteger(1, messageDigestEnconde.digest()).toString(16).toCharArray();
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, noSuchAlgorithmException);
        }

        return new String(encoded);
    }

    public String getEmail() {
        return email;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setEmail(String emaill) {
        this.email = emaill;
    }
}
