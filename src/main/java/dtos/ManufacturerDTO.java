package dtos;

import java.util.ArrayList;
import java.util.List;

public class ManufacturerDTO {

    private Long id;
    private String name;
    private String password;
    private String email;
    private List<ProductDTO> products;

    public ManufacturerDTO() {
        this.products = new ArrayList<>();
    }

    public ManufacturerDTO(Long id, String name, String password, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.products = new ArrayList<>();
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

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}
