package dtos;

import entities.Structure;

import java.util.ArrayList;
import java.util.List;

public class ProductDTO {

    private String name;
    private String client;
    private String designer;
    private List<Structure> structures;

    public ProductDTO() {
        this.structures = new ArrayList<>();
    }

    public ProductDTO(String name, String client, String designer) {
        this.name = name;
        this.client = client;
        this.designer = designer;
        this.structures = new ArrayList<>();
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

    public List<Structure> getStructures() {
        return structures;
    }

    public void setStructures(List<Structure> structures) {
        this.structures = structures;
    }
}
