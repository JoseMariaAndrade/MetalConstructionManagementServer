package dtos;

public class ProjectDTO {

    private String name;
    private String client;
    private String designer;

    public ProjectDTO() {
    }

    public ProjectDTO(String name, String client, String designer) {
        this.name = name;
        this.client = client;
        this.designer = designer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer;
    }
}
