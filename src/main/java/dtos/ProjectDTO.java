package dtos;

import entities.Structure;

import java.util.ArrayList;
import java.util.List;

public class ProjectDTO {

    private String name;
    private Long idClient;
    private String nameClient;
    private Long idDesigner;
    private String nameDesigner;
    private List<Structure> structures;

    public ProjectDTO() {
        this.structures = new ArrayList<>();
    }

    public ProjectDTO(String name, Long idClient, String nameClient, Long idDesigner, String nameDesigner) {
        this.name = name;
        this.idClient = idClient;
        this.nameClient = nameClient;
        this.idDesigner = idDesigner;
        this.nameDesigner = nameDesigner;
        this.structures = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    public Long getIdDesigner() {
        return idDesigner;
    }

    public void setIdDesigner(Long idDesigner) {
        this.idDesigner = idDesigner;
    }

    public String getNameDesigner() {
        return nameDesigner;
    }

    public void setNameDesigner(String nameDesigner) {
        this.nameDesigner = nameDesigner;
    }

    public List<Structure> getStructures() {
        return structures;
    }

    public void setStructures(List<Structure> structures) {
        this.structures = structures;
    }
}
