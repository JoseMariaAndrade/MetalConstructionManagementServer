package dtos;

import java.util.ArrayList;
import java.util.List;

public class StructureDTO {

    private String name;
    private String project;
    private List<ProductDTO> products;
    private int nb; // número de vãos
    private Double LVao; // comprimento dos vãos
    private int q; // sobrecarga atuante

    public StructureDTO() {
        this.products = new ArrayList<>();
    }

    public StructureDTO(String name, String project) {
        this.name = name;
        this.project = project;
        this.products = new ArrayList<>();
    }

    public StructureDTO(String name, String project, int nb, Double LVao, int q) {
        this.name = name;
        this.project = project;
        this.nb = nb;
        this.LVao = LVao;
        this.q = q;
    }

    public StructureDTO(String name, String project, List<ProductDTO> products, int nb, Double LVao, int q) {
        this.name = name;
        this.project = project;
        this.products = products;
        this.nb = nb;
        this.LVao = LVao;
        this.q = q;
    }

    public int getNb() {
        return nb;
    }

    public void setNb(int nb) {
        this.nb = nb;
    }

    public Double getLVao() {
        return LVao;
    }

    public void setLVao(Double LVao) {
        this.LVao = LVao;
    }

    public int getQ() {
        return q;
    }

    public void setQ(int q) {
        this.q = q;
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
