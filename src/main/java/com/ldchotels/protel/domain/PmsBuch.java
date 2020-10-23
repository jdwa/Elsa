package com.ldchotels.protel.domain;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Protel buch entity.\n@author JD
 */
@ApiModel(description = "Protel buch entity.\n@author JD")
@Entity
@Table(name = "buch")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PmsBuch implements Serializable {
    private static final long serialVersionUID = 1L;

    /*
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    */
    @Id
    @Column(name = "buchnr", unique = true)
    private Long id;

    @Column(name = "datumvon")
    private LocalDate datumvon;

    @Column(name = "datumbis")
    private LocalDate datumbis;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumvon() {
        return datumvon;
    }

    public PmsBuch datumvon(LocalDate datumvon) {
        this.datumvon = datumvon;
        return this;
    }

    public void setDatumvon(LocalDate datumvon) {
        this.datumvon = datumvon;
    }

    public LocalDate getDatumbis() {
        return datumbis;
    }

    public PmsBuch datumbis(LocalDate datumbis) {
        this.datumbis = datumbis;
        return this;
    }

    public void setDatumbis(LocalDate datumbis) {
        this.datumbis = datumbis;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PmsBuch)) {
            return false;
        }
        return id != null && id.equals(((PmsBuch) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PmsBuch{" +
            "id=" + getId() +
            ", datumvon='" + getDatumvon() + "'" +
            ", datumbis='" + getDatumbis() + "'" +
            "}";
    }
}
