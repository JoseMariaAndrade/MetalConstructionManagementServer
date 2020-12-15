package dtos;

public class FamilyProductDTO {

    private String name;

    public FamilyProductDTO() {
    }

    public FamilyProductDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
