package ma.ac.um5.ensias.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Situation.
 */
@Entity
@Table(name = "situation")
public class Situation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "volume")
    private Float volume;

    @Column(name = "remplissage")
    private Float remplissage;

    @ManyToOne
    @JsonIgnoreProperties(value = "situations", allowSetters = true)
    private Poubelle poubelle;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Situation date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Float getVolume() {
        return volume;
    }

    public Situation volume(Float volume) {
        this.volume = volume;
        return this;
    }

    public void setVolume(Float volume) {
        this.volume = volume;
    }

    public Float getRemplissage() {
        return remplissage;
    }

    public Situation remplissage(Float remplissage) {
        this.remplissage = remplissage;
        return this;
    }

    public void setRemplissage(Float remplissage) {
        this.remplissage = remplissage;
    }

    public Poubelle getPoubelle() {
        return poubelle;
    }

    public Situation poubelle(Poubelle poubelle) {
        this.poubelle = poubelle;
        return this;
    }

    public void setPoubelle(Poubelle poubelle) {
        this.poubelle = poubelle;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Situation)) {
            return false;
        }
        return id != null && id.equals(((Situation) o).id);
    }

    public Situation(LocalDate date, Float volume, Float remplissage, Poubelle poubelle) {
        this.date = date;
        this.volume = volume;
        this.remplissage = remplissage;
        this.poubelle = poubelle;
    }

    public Situation() {
        super();
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Situation{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", volume=" + getVolume() +
            ", remplissage=" + getRemplissage() +
            "}";
    }
}
