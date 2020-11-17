package dtos;

import java.io.Serializable;

public class DocumentDTO implements Serializable {

    private int id;
    private String filename;
    private String filepath;

    public DocumentDTO() {

    }

    public DocumentDTO(int id, String filename, String filepath) {
        this.id = id;
        this.filename = filename;
        this.filepath = filepath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
