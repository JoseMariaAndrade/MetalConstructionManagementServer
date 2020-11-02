package dtos;

import java.util.ArrayList;
import java.util.List;

public class StructureDTO {

    private String name;
    private String project;
    private List<ProductDTO> products;

    public StructureDTO() {
        this.products = new ArrayList<>();
    }

    public StructureDTO(String name, String project) {
        this.name = name;
        this.project = project;
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
}
