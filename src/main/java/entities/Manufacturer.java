package entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "getAllManufacturers", query = "SELECT m FROM Manufacturer m ORDER BY m.name") //JPQL
})
public class Manufacturer extends User implements Serializable {

    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.REMOVE)
    private List<Product> products;

    public Manufacturer() {
        super();
        this.products = new ArrayList<>();
    }

    public Manufacturer(@NotNull String name, @NotNull String password, @NotNull @Email String emaill) {
        super(name, password, emaill);
        this.products = new ArrayList<>();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
