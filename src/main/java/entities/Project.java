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

    /*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    */
    @OneToMany
    private List<Document> documents;
    @Id
    @NotNull
    private String name;
    @ManyToOne
    @JoinColumn(name = "PROJECT_CLIENT_NAME")
    @NotNull
    private Client client;
    @ManyToOne
    @JoinColumn(name = "PROJECT_DESIGNER_NAME")
    @NotNull
    private Designer designer;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    public List<Structure> structures;
    @Version
    private int version;
    private boolean isApproved;
    private String observations;

    public Project() {
        this.documents = new ArrayList<>();
        this.structures = new ArrayList<>();
    }

    public Project(String name, Client client, Designer designer) {
        this.name = name;
        this.client = client;
        this.designer = designer;
        this.documents = new ArrayList<>();
        this.structures = new ArrayList<>();
        this.isApproved = false;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public List<Document> getDocuments() {
            return documents;
        }

    public boolean isApproved() {
        return isApproved;
    }

    public void setContracted(boolean constracted) {
        isApproved = constracted;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

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


    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
