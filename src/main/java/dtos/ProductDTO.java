package dtos;

public class ProductDTO {

    private String name;
    private String type;
    private String family;
    private String manufacturer;

    public ProductDTO() {
    }

    public ProductDTO(String name, String type, String family, String manufacturer) {
        this.name = name;
        this.type = type;
        this.family = family;
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
