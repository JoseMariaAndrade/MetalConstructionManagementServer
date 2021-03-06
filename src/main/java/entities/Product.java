package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "PRODUCTS")
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
    @OneToMany(mappedBy = "produto", cascade = CascadeType.REMOVE)
    private List<Variante> variantes;
    @Version
    private int version;
    private Boolean needStock;

    public Product() {
        this.structures = new ArrayList<>();
        this.variantes = new LinkedList<>();
        this.needStock = false;
    }

    public Product(String name, @NotNull FamilyProduct familyProduct, @NotNull Manufacturer manufacturer) {
        this.name = name;
        this.familyProduct = familyProduct;
        this.manufacturer = manufacturer;
        this.structures = new ArrayList<>();
        this.variantes = new LinkedList<>();
        this.needStock = false;
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

    public List<Variante> getVariantes() {
        return variantes;
    }

    public void setVariantes(List<Variante> especimen) {
        this.variantes = especimen;
    }

    public void addVariante(Variante s) {
        variantes.add(s);
    }

    public void removeVariante(Variante s) {
        variantes.remove(s);
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Boolean getNeedStock() {
        return needStock;
    }

    public void setNeedStock(Boolean needStock) {
        this.needStock = needStock;
    }
}
