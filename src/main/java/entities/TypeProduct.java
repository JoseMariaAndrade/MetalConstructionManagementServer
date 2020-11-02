package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "getAllTypesProduct", query = "SELECT t FROM TypeProduct t ORDER BY t.description") //JPQL
})
public class TypeProduct implements Serializable {

    @Id
    public String description;
    @OneToMany(mappedBy = "typeProduct")
    private List<Product> products;

    public TypeProduct() {
        this.products = new ArrayList<>();
    }

    public TypeProduct(String description) {
        this.description = description;
        this.products = new ArrayList<>();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
