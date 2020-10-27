package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class FamilyProduct implements Serializable {

    @Id
    private String name;

    public FamilyProduct() {
    }

    public FamilyProduct(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
