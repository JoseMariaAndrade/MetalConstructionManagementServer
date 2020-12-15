package dtos;

import entities.Structure;

import java.util.ArrayList;
import java.util.List;

public class ProjectDTO {

    private String name;
    private String client;
    private String designer;
    private List<StructureDTO> structures;
    private boolean isApproved;

    public ProjectDTO() {
        this.structures = new ArrayList<>();
    }

    public ProjectDTO(String name, String client, String designer, List<StructureDTO> structureDTOs) {
        this.name = name;
        this.client = client;
        this.designer = designer;
        this.structures = structureDTOs;
    }

    public ProjectDTO(String name, String client, String designer, List<StructureDTO> structureDTOs, boolean isApproved) {
        this.name = name;
        this.client = client;
        this.designer = designer;
        this.structures = structureDTOs;
        this.isApproved = isApproved;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer;
    }

    public List<StructureDTO> getStructures() {
        return structures;
    }

    public void setStructures(List<StructureDTO> structures) {
        this.structures = structures;
    }
}
