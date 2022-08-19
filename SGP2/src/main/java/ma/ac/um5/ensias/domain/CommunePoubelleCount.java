package ma.ac.um5.ensias.domain;

import java.io.Serializable;

public class CommunePoubelleCount implements Serializable {

    private static final long serialVersionUID = 1L;

    public String commune;
    public Long totalPoubelle;

    public CommunePoubelleCount(String commune, long totalPoubelle) {
        this.commune = commune;
        this.totalPoubelle = totalPoubelle;
    }

    public CommunePoubelleCount() {
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public Long getTotalPoubelle() {
        return totalPoubelle;
    }

    public void setTotalPoubelle(Long totalPoubelle) {
        this.totalPoubelle = totalPoubelle;
    }
}
