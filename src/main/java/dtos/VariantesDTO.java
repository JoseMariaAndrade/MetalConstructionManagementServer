package dtos;

public class VariantesDTO {

    private String nome;

    public VariantesDTO() {
    }

    public VariantesDTO(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
