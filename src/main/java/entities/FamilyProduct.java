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
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "FAMILY_TYPE_DESCRIPTION")
    private TypeProduct type;
    @OneToMany(mappedBy = "familyProduct")
    private List<Product> products;

    public FamilyProduct() {
        this.products = new ArrayList<>();
    }

    public FamilyProduct(String name, TypeProduct typeProduct) {
        this.name = name;
        this.type = typeProduct;
        this.products = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeProduct getTypeProduct() {
        return type;
    }

    public void setTypeProduct(TypeProduct typeProduct) {
        this.type = typeProduct;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
