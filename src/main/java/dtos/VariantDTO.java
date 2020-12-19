package dtos;

public class VariantDTO {

    private int codigo;
    private String produto;
    private String nome;
    private String displayName;

    public VariantDTO(int codigo, String displayName) {
        this.codigo = codigo;
        this.displayName = displayName;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
