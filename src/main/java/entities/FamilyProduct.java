package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "getAllFamiliesProduct", query = "SELECT f FROM FamilyProduct f ORDER BY f.name")
})
public class FamilyProduct implements Serializable {

    @Id
    private String name;
    @OneToMany(mappedBy = "familyProduct")
    private List<Product> products;

    public FamilyProduct() {
        this.products = new ArrayList<>();
    }

    public FamilyProduct(String name) {
        this.name = name;
        this.products = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
    }
}
