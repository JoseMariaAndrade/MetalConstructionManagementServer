package dtos;

import entities.Variante;

import java.util.ArrayList;
import java.util.List;

public class ProductDTO {

    private String name;
    private String type;
    private String family;
    private Boolean needStock;
    private Long manufacturer;
    private String manufacturerName;
    private List<VariantesDTO> variantes;
    private List<VariantDTO> variants;


    public ProductDTO() {
        this.variantes = new ArrayList<>();
        this.variants = new ArrayList<>();
    }

    public ProductDTO(String name, String type, String family, Long manufacturer) {
        this.name = name;
        this.type = type;
        this.family = family;
        this.manufacturer = manufacturer;
        this.variantes = new ArrayList<>();
        this.variants = new ArrayList<>();
    }

    public ProductDTO(String name, String type, String family) {
        this.name = name;
        this.type = type;
        this.family = family;
        this.variantes = new ArrayList<>();
        this.variants = new ArrayList<>();
    }

    public ProductDTO(String name, Boolean needStock, String type, String family, List<VariantDTO> variants) {
        this.name = name;
        this.type = type;
        this.family = family;
        this.needStock = needStock;
        this.variants = variants;
        this.variantes = new ArrayList<>();
    }

    public ProductDTO(String name, Boolean needStock, String type, String family) {
        this.name = name;
        this.needStock = needStock;
        this.type = type;
        this.family = family;
        this.variantes = new ArrayList<>();
        this.variants = new ArrayList<>();
    }

    public ProductDTO(String name, String family) {
        this.name = name;
        this.family = family;
        this.variantes = new ArrayList<>();
        this.variants = new ArrayList<>();
    }

    public ProductDTO(String name, String type, String family, String manufacturerName) {
        this.name = name;
        this.type = type;
        this.family = family;
        this.manufacturerName = manufacturerName;
        this.variantes = new ArrayList<>();
    }

    public ProductDTO(String name, String type, String family, String manufacturerName, List<VariantDTO> variants) {
        this.name = name;
        this.type = type;
        this.family = family;
        this.manufacturerName = manufacturerName;
        this.variants = variants;
        this.variantes = new ArrayList<>();
    }

    public List<VariantDTO> getVariants() {
        return variants;
    }

    public void setVariants(List<VariantDTO> variants) {
        this.variants = variants;
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

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public List<VariantesDTO> getVariantes() {
        return variantes;
    }

    public void setVariantes(List<VariantesDTO> variantes) {
        this.variantes = variantes;
    }
}
