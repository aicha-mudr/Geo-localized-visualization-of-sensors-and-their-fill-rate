package ma.ac.um5.ensias.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poiji.annotation.ExcelCell;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Poubelle.
 */
@Entity
@Table(name = "poubelle")
public class Poubelle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ExcelCell(8)
    @Column(name = "ref_okko", unique = true)
    private String ref_okko;

    @ExcelCell(7)
    @Column(name = "ref_mineris")
    private String ref_mineris;

    @ExcelCell(2)
    @Column(name = "ref_produit")
    private String ref_produit;

    @ManyToOne
    @JsonIgnoreProperties(value = "poubelles", allowSetters = true)
    private Localisation localisation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRef_okko() {
        return ref_okko;
    }

    public Poubelle ref_okko(String ref_okko) {
        this.ref_okko = ref_okko;
        return this;
    }

    public void setRef_okko(String ref_okko) {
        this.ref_okko = ref_okko;
    }

    public String getRef_mineris() {
        return ref_mineris;
    }

    public Poubelle ref_mineris(String ref_mineris) {
        this.ref_mineris = ref_mineris;
        return this;
    }

    public void setRef_mineris(String ref_mineris) {
        this.ref_mineris = ref_mineris;
    }

    public String getRef_produit() {
        return ref_produit;
    }

    public Poubelle ref_produit(String ref_produit) {
        this.ref_produit = ref_produit;
        return this;
    }

    public void setRef_produit(String ref_produit) {
        this.ref_produit = ref_produit;
    }

    public Localisation getLocalisation() {
        return localisation;
    }

    public Poubelle localisation(Localisation localisation) {
        this.localisation = localisation;
        return this;
    }

    public Poubelle() {
        super();
    }

    public Poubelle(String ref_okko, String ref_mineris, String ref_produit, Localisation localisation) {
        this.ref_okko = ref_okko;
        this.ref_mineris = ref_mineris;
        this.ref_produit = ref_produit;
        this.localisation = localisation;
    }

    public void setLocalisation(Localisation localisation) {
        this.localisation = localisation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Poubelle)) {
            return false;
        }
        return id != null && id.equals(((Poubelle) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Poubelle{" +
            "id=" + getId() +
            ", ref_okko='" + getRef_okko() + "'" +
            ", ref_mineris='" + getRef_mineris() + "'" +
            ", ref_produit='" + getRef_produit() + "'" +
            "}";
    }
}
