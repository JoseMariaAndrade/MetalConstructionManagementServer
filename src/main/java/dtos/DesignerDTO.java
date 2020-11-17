package dtos;

import java.util.ArrayList;
import java.util.List;

public class DesignerDTO {

    private Long id;
    private String name;
    private String password;
    private String email;
    private List<ProjectDTO> projects;

    public DesignerDTO() {
        this.projects = new ArrayList<>();
    }

    public DesignerDTO(Long id, String name, String password, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.projects = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ProjectDTO> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDTO> projects) {
        this.projects = projects;
    }
}
