package entities;

import javax.persistence.*;

@Entity
@NamedQuery(name = "getProjectDocuments", query = "SELECT doc FROM Document doc WHERE doc.project.name = :projectName")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String filepath;
    private String filename;
    @ManyToOne
    private Project project;

    public Document() {

    }

    public Document(String filepath, String filename, Project project) {
        this.filepath = filepath;
        this.filename = filename;
        this.project = project;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
