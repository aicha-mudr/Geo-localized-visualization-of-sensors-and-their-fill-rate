package ma.ac.um5.ensias.domain;


import com.poiji.annotation.ExcelCell;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Localisation.
 */
@Entity
@Table(name = "localisation")
public class Localisation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ExcelCell(0)
    @Column(name = "collectivite_1")
    private String collectivite_1;

    @ExcelCell(1)
    @Column(name = "collectivite_2")
    private String collectivite_2;

    @ExcelCell(3)
    @Column(name = "commune")
    private String commune;

    @ExcelCell(5)
    @Column(name = "adresse")
    private String adresse;

    @ExcelCell(11)
    @Column(name = "logitude")
    private Float logitude;

    @ExcelCell(12)
    @Column(name = "latitude")
    private Float latitude;

    @ExcelCell(8)
    @Transient
    private String ref_okko;

    @ExcelCell(7)
    @Transient
    private String ref_mineris;

    @ExcelCell(2)
    @Transient
    private String ref_produit;

    @ExcelCell(10)
    @Transient
    private Float remplissage;

    @Transient
    @ExcelCell(6)
    private Float volume;

    @Transient
    @ExcelCell(9)
//    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private String date;




    public String getRef_okko() {
        return ref_okko;
    }

    public void setRef_okko(String ref_okko) {
        this.ref_okko = ref_okko;
    }

    public String getRef_mineris() {
        return ref_mineris;
    }

    public void setRef_mineris(String ref_mineris) {
        this.ref_mineris = ref_mineris;
    }

    public String getRef_produit() {
        return ref_produit;
    }

    public void setRef_produit(String ref_produit) {
        this.ref_produit = ref_produit;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCollectivite_1() {
        return collectivite_1;
    }

    public Localisation collectivite_1(String collectivite_1) {
        this.collectivite_1 = collectivite_1;
        return this;
    }

    public void setCollectivite_1(String collectivite_1) {
        this.collectivite_1 = collectivite_1;
    }

    public String getCollectivite_2() {
        return collectivite_2;
    }

    public Localisation collectivite_2(String collectivite_2) {
        this.collectivite_2 = collectivite_2;
        return this;
    }

    public void setCollectivite_2(String collectivite_2) {
        this.collectivite_2 = collectivite_2;
    }

    public String getCommune() {
        return commune;
    }

    public Localisation commune(String commune) {
        this.commune = commune;
        return this;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getAdresse() {
        return adresse;
    }

    public Localisation adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Float getLogitude() {
        return logitude;
    }

    public Localisation logitude(Float logitude) {
        this.logitude = logitude;
        return this;
    }

    public void setLogitude(Float logitude) {
        this.logitude = logitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Localisation latitude(Float latitude) {
        this.latitude = latitude;
        return this;
    }

    public Float getRemplissage() {
        return remplissage;
    }

    public void setRemplissage(Float remplissage) {
        this.remplissage = remplissage;
    }

    public Float getVolume() {
        return volume;
    }

    public void setVolume(Float volume) {
        this.volume = volume;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Localisation)) {
            return false;
        }
        return id != null && id.equals(((Localisation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Localisation{" +
            "id=" + getId() +
            ", collectivite_1='" + getCollectivite_1() + "'" +
            ", collectivite_2='" + getCollectivite_2() + "'" +
            ", commune='" + getCommune() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", logitude=" + getLogitude() +
            ", latitude=" + getLatitude() +
            "}";
    }
}
