package dtos;

public class FamilyProductDTO {

    private String name;
    private String typeProduct;

    public FamilyProductDTO() {
    }

    public FamilyProductDTO(String name) {
        this.name = name;
    }

    public FamilyProductDTO(String name, String typeProduct) {
        this.name = name;
        this.typeProduct = typeProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeProduct() {
        return typeProduct;
    }

    public void setTypeProduct(String typeProduct) {
        this.typeProduct = typeProduct;
    }
}
