package dtos;

public class SimulationDTO {

    private int nb;
    private Double lvao;
    private int q;
    private int variantCode;

    public SimulationDTO() {
    }

    public SimulationDTO(int nb, Double lvao, int q, int variantCode) {
        this.nb = nb;
        this.lvao = lvao;
        this.q = q;
        this.variantCode = variantCode;
    }

    public int getNb() {
        return nb;
    }

    public void setNb(int nb) {
        this.nb = nb;
    }

    public Double getLvao() {
        return lvao;
    }

    public void setLvao(Double lvao) {
        this.lvao = lvao;
    }

    public int getQ() {
        return q;
    }

    public void setQ(int q) {
        this.q = q;
    }

    public int getVariantCode() {
        return variantCode;
    }

    public void setVariantCode(int variantCode) {
        this.variantCode = variantCode;
    }
}
