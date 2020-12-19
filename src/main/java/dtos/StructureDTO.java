package dtos;

import java.util.ArrayList;
import java.util.List;

public class StructureDTO {

    private String name;
    private String project;
    private List<ProductDTO> products;
    private Boolean decision;
    private String observation;

    public StructureDTO() {
        this.products = new ArrayList<>();
    }

    public StructureDTO(String name, String project, Boolean decision, String observation) {
        this.name = name;
        this.project = project;
        this.decision = decision;
        this.observation = observation;
        this.products = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public Boolean getDecision() {
        return decision;
    }

    public void setDecision(Boolean decision) {
        this.decision = decision;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
