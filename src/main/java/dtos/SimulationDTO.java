package dtos;

public class SimulationDTO {

    private int nb;
    private Double LVao;
    private int q;
    private int variantCode;

    public SimulationDTO() {
    }

    public SimulationDTO(int nb, Double LVao, int q, int variantCode) {
        this.nb = nb;
        this.LVao = LVao;
        this.q = q;
        this.variantCode = variantCode;
    }

    public SimulationDTO(int nb, Double LVao, int q) {
        this.nb = nb;
        this.LVao = LVao;
        this.q = q;
    }

    public int getNb() {
        return nb;
    }

    public void setNb(int nb) {
        this.nb = nb;
    }

    public Double getLVao() {
        return LVao;
    }

    public void setLVao(Double LVao) {
        this.LVao = LVao;
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
