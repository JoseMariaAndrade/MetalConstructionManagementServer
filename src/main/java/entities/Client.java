package entities;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Client extends User implements Serializable {

    @OneToMany(mappedBy = "client")
    private List<Project> projects;

    public Client() {
        super();
    }

    public Client(@NotNull String name, @NotNull @Email String email) {
        super(name, email);
        this.projects = new ArrayList<>();
    }
}
