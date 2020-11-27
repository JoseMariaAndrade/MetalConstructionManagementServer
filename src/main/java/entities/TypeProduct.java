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
    @OneToMany(mappedBy = "type")
    public List<FamilyProduct> familyProduct;

    public TypeProduct() {
        this.familyProduct = new ArrayList<>();
    }

    public TypeProduct(String description) {
        this.description = description;
        this.familyProduct = new ArrayList<>();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<FamilyProduct> getFamilyProduct() {
        return familyProduct;
    }

    public void setFamilyProduct(List<FamilyProduct> familyProduct) {
        this.familyProduct = familyProduct;
    }
}
