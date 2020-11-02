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
    @ManyToOne
    @JoinColumn(name = "PROJECT_CLIENT_NAME")
    @NotNull
    private Client client;
    @ManyToOne
    @JoinColumn(name = "PROJECT_DESIGNER_NAME")
    @NotNull
    private Designer designer;
    //public List<File> files;
    @OneToMany(mappedBy = "project")
    public List<Structure> structures;
    @Version
    private int version;

    public Project() {
//        this.files = new ArrayList<>();
        this.structures = new ArrayList<>();
    }

    public Project(String name, Client client, Designer designer) {
        this.name = name;
        this.client = client;
        this.designer = designer;
//        this.files = new ArrayList<>();
//        this.structures = new ArrayList<>();
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

    //    public List<File> getFiles() {
//        return files;
//    }
//
//    public void setFiles(List<File> files) {
//        this.files = files;
//    }
//
//    public List<Structure> getStructures() {
//        return structures;
//    }
//
//    public void setStructures(List<Structure> structures) {
//        this.structures = structures;
//    }


    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
