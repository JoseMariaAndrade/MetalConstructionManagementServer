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
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    public List<Structure> structures;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "PROJECT_CLIENT_NAME")
    @NotNull
    private Client client;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "PROJECT_DESIGNER_NAME")
    @NotNull
    private Designer designer;
    @OneToMany(mappedBy = "project")
    private List<Document> documents;
    @Version
    private int version;
    private Boolean availableToClient;

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
        this.availableToClient = false;
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

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Boolean getAvailableToClient() {
        return availableToClient;
    }

    public void setAvailableToClient(Boolean availableToClient) {
        this.availableToClient = availableToClient;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
