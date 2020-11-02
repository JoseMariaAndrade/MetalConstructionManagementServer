package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "getAllProducts", query = "SELECT p FROM Product p ORDER BY p.name")
})
public class Product implements Serializable {

    @Id
    private String name;
    @ManyToOne
    @JoinColumn(name = "TYPE_PRODUCT_NAME")
    @NotNull
    private TypeProduct typeProduct;
    @ManyToOne
    @JoinColumn(name = "FAMILY_PRODUCT_NAME")
    @NotNull
    private FamilyProduct familyProduct;
    @ManyToMany
    @JoinTable(name = "PRODUCTS_STRUCTURES",
            joinColumns = @JoinColumn(name = "PRODUCT_NAME", referencedColumnName = "NAME"),
            inverseJoinColumns = @JoinColumn(name = "STRUCTURE_NAME", referencedColumnName = "NAME"))
    private List<Structure> structures;


    public Product() {
        this.structures = new ArrayList<>();
    }

    public Product(String name, @NotNull TypeProduct typeProduct, @NotNull FamilyProduct familyProduct) {
        this.name = name;
        this.typeProduct = typeProduct;
        this.familyProduct = familyProduct;
        this.structures = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeProduct getTypeProduct() {
        return typeProduct;
    }

    public void setTypeProduct(TypeProduct typeProduct) {
        this.typeProduct = typeProduct;
    }

    public FamilyProduct getFamilyProduct() {
        return familyProduct;
    }

    public void setFamilyProduct(FamilyProduct familyProduct) {
        this.familyProduct = familyProduct;
    }

    public List<Structure> getStructures() {
        return structures;
    }

    public void setStructures(List<Structure> structures) {
        this.structures = structures;
    }
}
