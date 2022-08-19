package ma.ac.um5.ensias.domain;

import java.io.Serializable;

public class Moyenne implements Serializable {
    private static final long serialVersionUID = 1L;

    public String refPoubelle;
    public Float moyenne;
    public Long year;
    public Long mois;


    public Long getMois() {
        return mois;
    }

    public void setMois(Long mois) {
        this.mois = mois;
    }

    public Moyenne(String refPoubelle, Float moyenne, Long year, Long mois) {
        this.refPoubelle = refPoubelle;
        this.moyenne = moyenne;
        this.year = year;
        this.mois=mois;
    }

    public String getRefPoubelle() {
        return refPoubelle;
    }

    public void setRefPoubelle(String refPoubelle) {
        this.refPoubelle = refPoubelle;
    }

    public Float getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(Float moyenne) {
        this.moyenne = moyenne;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }
}
