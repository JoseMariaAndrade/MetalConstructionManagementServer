package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Product implements Serializable {

    @Id
    private String name;
    @NotNull
    private TypeProduct typeProduct;
    @NotNull
    private FamilyProduct familyProduct;


    public Product() {
    }

    public Product(String name, @NotNull TypeProduct typeProduct, @NotNull FamilyProduct familyProduct) {
        this.name = name;
        this.typeProduct = typeProduct;
        this.familyProduct = familyProduct;
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
}
