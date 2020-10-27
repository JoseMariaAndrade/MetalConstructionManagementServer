package dtos;

import java.util.ArrayList;
import java.util.List;

public class DesignerDTO {

    private String name;
    private String email;
    private List<ProjectDTO> projects;

    public DesignerDTO() {
        this.projects = new ArrayList<>();
    }

    public DesignerDTO(String name, String email) {
        this.name = name;
        this.email = email;
        this.projects = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
