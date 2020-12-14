package entities;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "getAllDesigners", query = "SELECT d FROM Designer d ORDER BY d.name") //JPQL
})
public class Designer extends User implements Serializable {

    @OneToMany(mappedBy = "designer")
    private List<Project> projects;

    public Designer() {
        super();
        this.projects = new ArrayList<>();
    }

    public Designer(@NotNull String name, @NotNull String password, @NotNull @Email String emaill) {
        super(name, password, emaill);
        this.projects = new ArrayList<>();
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
