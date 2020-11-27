package dtos;

import java.util.List;

public class TypeProductDTO {

    private String description;
    private List<FamilyProductDTO> families;

    public TypeProductDTO() {
    }

    public TypeProductDTO(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<FamilyProductDTO> getFamilies() {
        return families;
    }

    public void setFamilies(List<FamilyProductDTO> families) {
        this.families = families;
    }
}
