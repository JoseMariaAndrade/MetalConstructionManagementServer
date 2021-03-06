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
    private List<Product> products;
    private Boolean decision;
    private Boolean feito;
    private String observation;

    private int nb; // número de vãos
    private Double LVao; // comprimento dos vãos
    private int q; // sobrecarga atuante

    public Structure() {
        this.products = new ArrayList<>();
        this.feito = false;
        this.decision = null;
        this.observation = null;
    }

    public Structure(String name, @NotNull Project project) {
        this.name = name;
        this.project = project;
        this.products = new ArrayList<>();
        this.decision = null;
        this.feito = false;
        this.observation = null;
    }

    public Structure(String name, @NotNull Project project, int nb, Double LVao, int q) {
        this.name = name;
        this.project = project;
        this.nb = nb;
        this.LVao = LVao;
        this.q = q;
        this.products = new ArrayList<>();
        this.decision = null;
        this.feito = false;
        this.observation = null;
    }

    public int getNb() {
        return nb;
    }

    public void setNb(int nb) {
        this.nb = nb;
    }

    public Double getLVao() {
        return LVao;
    }

    public void setLVao(Double LVao) {
        this.LVao = LVao;
    }

    public int getQ() {
        return q;
    }

    public void setQ(int q) {
        this.q = q;
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
