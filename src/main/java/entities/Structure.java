package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "STRUCTURES")
@NamedQueries({
        @NamedQuery(name = "getAllStructures", query = "SELECT s FROM Structure s ORDER BY s.name")
})
public class Structure implements Serializable {

    @Id
    private String name;
    @ManyToOne
    @JoinColumn(name = "PROJECT_NAME")
    @NotNull
    private Project project;
    @ManyToMany(mappedBy = "structures")
    @NotNull
    private List<Product> products;

    public Structure() {
        this.products = new ArrayList<>();
    }

    public Structure(String name, @NotNull Project project) {
        this.name = name;
        this.project = project;
        this.products = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
