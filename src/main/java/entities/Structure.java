package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "STRUCTURES")
@NamedQueries({
        @NamedQuery(name = "getAllStructures", query = "SELECT s FROM Structure s ORDER BY s.name"),
        @NamedQuery(name = "getAllStructuresOfProjectApprovedAndNotDone", query = "SELECT s FROM Structure s WHERE s.decision=TRUE AND s.feito=FALSE")
})
public class Structure implements Serializable {

    @Id
    private String name;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "PROJECT_NAME")
    @NotNull
    private Project project;
    @ManyToMany(mappedBy = "structures", cascade = CascadeType.REMOVE)
    @NotNull
    private List<Product> products;
    private Boolean decision;
    private Boolean feito;
    private String observation;

    public Structure() {
        this.products = new ArrayList<>();
        this.feito = false;
    }

    public Structure(String name, @NotNull Project project) {
        this.name = name;
        this.project = project;
        this.products = new ArrayList<>();
        this.decision = null;
        this.feito = false;
        this.observation = null;
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

    public Boolean getDecision() {
        return decision;
    }

    public void setDecision(Boolean decision) {
        this.decision = decision;
    }

    public Boolean getFeito() {
        return feito;
    }

    public void setFeito(Boolean feito) {
        this.feito = feito;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
