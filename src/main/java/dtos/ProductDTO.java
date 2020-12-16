package dtos;

public class ProductDTO {

    private String name;
    private String type;
    private String family;
    private Boolean needStock;
    private Long manufacturer;

    public ProductDTO() {
    }

    public ProductDTO(String name, String type, String family, Long manufacturer) {
        this.name = name;
        this.type = type;
        this.family = family;
        this.manufacturer = manufacturer;
    }

    public ProductDTO(String name, String type, String family) {
        this.name = name;
        this.type = type;
        this.family = family;
    }

    public ProductDTO(String name, Boolean needStock, String type, String family) {
        this.name = name;
        this.needStock = needStock;
        this.type = type;
        this.family = family;
    }

    public ProductDTO(String name, String family) {
        this.name = name;
        this.family = family;
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

    public Boolean getNeedStock() {
        return needStock;
    }

    public void setNeedStock(Boolean needStock) {
        this.needStock = needStock;
    }

    public Long getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Long manufacturer) {
        this.manufacturer = manufacturer;
    }
}
