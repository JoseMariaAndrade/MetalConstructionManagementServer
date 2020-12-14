package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "PROJECTS")
@NamedQueries({
        @NamedQuery(name = "getAllProjects", query = "SELECT p FROM Project p ORDER BY p.name") //JPQL
})
public class Project implements Serializable {

    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;*/
    @Id
    @NotNull
    private String name;
    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    public List<Structure> structures;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "PROJECT_CLIENT_NAME")
    @NotNull
    private Client client;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "PROJECT_DESIGNER_NAME")
    @NotNull
    private Designer designer;
    private Boolean decision;
    private String observation;
    @OneToMany(mappedBy = "project")
    private List<Document> documents;
//    @Version
//    private int version;

    public Project() {
        this.documents = new ArrayList<>();
        this.structures = new ArrayList<>();
    }

    public Project(String name, Client client, Designer designer) {
        this.name = name;
        this.client = client;
        this.designer = designer;
        this.decision = null;
        this.observation = null;
        this.documents = new ArrayList<>();
        this.structures = new ArrayList<>();
    }

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Designer getDesigner() {
        return designer;
    }

    public void setDesigner(Designer designer) {
        this.designer = designer;
    }

    public List<Structure> getStructures() {
        return structures;
    }

    public void setStructures(List<Structure> structures) {
        this.structures = structures;
    }

    public Boolean getDecision() {
        return decision;
    }

    public void setDecision(Boolean decision) {
        this.decision = decision;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

//    public int getVersion() {
//        return version;
//    }
//
//    public void setVersion(int version) {
//        this.version = version;
//    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
