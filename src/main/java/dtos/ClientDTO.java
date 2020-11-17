package dtos;

import java.util.ArrayList;
import java.util.List;

public class ClientDTO {

    private Long id;
    private String name;
    private String password;
    private String email;
    private String contact;
    private String address;
    private List<ProjectDTO> projects;

    public ClientDTO() {
        this.projects = new ArrayList<>();
    }

    public ClientDTO(Long id, String name, String password, String email, String contact, String address) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.contact = contact;
        this.address = address;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ProjectDTO> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDTO> projects) {
        this.projects = projects;
    }
}
