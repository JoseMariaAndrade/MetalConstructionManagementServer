package entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "getAllDesigners", query = "SELECT d FROM Designer d ORDER BY d.name") //JPQL
})
public class Designer extends User{

    @OneToMany(mappedBy = "designer")
    private List<Project> projects;
    @Version
    private int version;

    public Designer() {
        super();
        this.projects = new ArrayList<>();
    }

    public Designer(@NotNull String name, @NotNull @Email String emaill) {
        super(name, emaill);
        this.projects = new ArrayList<>();
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
