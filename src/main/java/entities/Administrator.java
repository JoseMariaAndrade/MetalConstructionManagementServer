package entities;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Administrator extends User implements Serializable {

    public Administrator() {
        super();
    }

    public Administrator(@NotNull String name, @NotNull String password, @NotNull @Email String email) {
        super(name, password, email);
    }
}
