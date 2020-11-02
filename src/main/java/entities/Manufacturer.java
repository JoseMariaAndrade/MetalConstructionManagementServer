package entities;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
public class Manufacturer extends User {

    //private List<Product> products;

    public Manufacturer() {
        super();
    }

    public Manufacturer(@NotNull String name, @NotNull @Email String emaill) {
        super(name, emaill);
    }
}
