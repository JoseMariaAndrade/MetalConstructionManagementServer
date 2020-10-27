package entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Designer extends User{

    @OneToMany(mappedBy = "designer")
    private List<Project> projects;

    public Designer() {
    }

    public Designer(@NotNull String name, @NotNull @Email String emaill) {

        super(name, emaill);
        this.projects = new ArrayList<>();
    }
}
