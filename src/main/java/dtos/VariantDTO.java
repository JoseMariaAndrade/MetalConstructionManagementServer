package dtos;

public class VariantDTO {

    private int codigo;
    private String produto;
    private String nome;
    private String displayName;
    private Double weff_p;
    private Double weff_n;
    private Double ar;
    private Double sigmaC;

    public VariantDTO() {
    }

    public VariantDTO(int codigo, String displayName) {
        this.codigo = codigo;
        this.displayName = displayName;
    }

    public VariantDTO(int codigo, String produto, String nome) {
        this.codigo = codigo;
        this.produto = produto;
        this.nome = nome;
    }

    public VariantDTO(int codigo, String produto, String nome, String displayName) {
        this.codigo = codigo;
        this.produto = produto;
        this.nome = nome;
        this.displayName = displayName;
    }

    public VariantDTO(int codigo, String produto, String nome, Double weff_p, Double weff_n, Double ar, Double sigmaC) {
        this.codigo = codigo;
        this.produto = produto;
        this.nome = nome;
        this.displayName = displayName;
        this.weff_p = weff_p;
        this.weff_n = weff_n;
        this.ar = ar;
        this.sigmaC = sigmaC;
    }

    public Double getWeff_p() {
        return weff_p;
    }

    public void setWeff_p(Double weff_p) {
        this.weff_p = weff_p;
    }

    public Double getWeff_n() {
        return weff_n;
    }

    public void setWeff_n(Double weff_n) {
        this.weff_n = weff_n;
    }

    public Double getAr() {
        return ar;
    }

    public void setAr(Double ar) {
        this.ar = ar;
    }

    public Double getSigmaC() {
        return sigmaC;
    }

    public void setSigmaC(Double sigmaC) {
        this.sigmaC = sigmaC;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
