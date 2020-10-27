package entities;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "getAllClients", query = "SELECT c FROM Client c ORDER BY c.name") //JPQL
})
public class Client extends User implements Serializable {

    @NotNull
    private String contact;
    @NotNull
    private String morada;
    @OneToMany(mappedBy = "client")
    private List<Project> projects;
    @Version
    private int version;

    public Client() {
        super();
    }

    public Client(@NotNull String name, @NotNull @Email String email) {
        super(name, email);
        this.projects = new ArrayList<>();
    }

    public Client(@NotNull String name, @NotNull @Email String emaill, @NotNull String contact, @NotNull String morada) {
        super(name, emaill);
        this.contact = contact;
        this.morada = morada;
        this.projects = new ArrayList<>();
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
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
