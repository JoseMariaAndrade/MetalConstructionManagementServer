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
    @JoinColumn(name = "FAMILY_PRODUCT_NAME")
    @NotNull
    private FamilyProduct familyProduct;
    @ManyToMany
    @JoinTable(name = "PRODUCTS_STRUCTURES",
            joinColumns = @JoinColumn(name = "PRODUCT_NAME", referencedColumnName = "NAME"),
            inverseJoinColumns = @JoinColumn(name = "STRUCTURE_NAME", referencedColumnName = "NAME"))
    private List<Structure> structures;
    @ManyToOne
    @JoinColumn(name = "MANUFACTURER_NAME")
    private Manufacturer manufacturer;


    public Product() {
        this.structures = new ArrayList<>();
    }

    public Product(String name, @NotNull FamilyProduct familyProduct, @NotNull Manufacturer manufacturer) {
        this.name = name;
        this.familyProduct = familyProduct;
        this.manufacturer = manufacturer;
        this.structures = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }
}
