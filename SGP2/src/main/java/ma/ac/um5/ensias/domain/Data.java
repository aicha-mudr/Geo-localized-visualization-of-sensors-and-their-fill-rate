package ma.ac.um5.ensias.domain;

import java.io.Serializable;

public class Data implements Serializable {
    private static final long serialVersionUID = 1L;




    private Long jour;
    private Long mois;
    private Long anne;
    private Long idPoubelle;
    private Long totalCommune;


    public Long getJour() {
        return jour;
    }

    public void setJour(Long jour) {
        this.jour = jour;
    }
    public Long getTotalCommune() {
        return totalCommune;
    }

    public void setTotalCommune(Long totalCommune) {
        this.totalCommune = totalCommune;
    }

    public Long getMois() {
        return mois;
    }

    public void setMois(Long mois) {
        this.mois = mois;
    }

    public Long getAnne() {
        return anne;
    }

    public void setAnne(Long anne) {
        this.anne = anne;
    }

    public Long getIdPoubelle() {
        return idPoubelle;
    }

    public void setIdPoubelle(Long idPoubelle) {
        this.idPoubelle = idPoubelle;
    }

}
