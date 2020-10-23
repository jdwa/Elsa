package com.ldchotels.protel.domain;

import com.google.errorprone.annotations.Immutable;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Protel kunden entity.\n@author JD
 */
@ApiModel(description = "Protel kunden entity.\n@author JD")
@Entity
@Immutable
@Table(name = "kunden")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PmsKunden implements Serializable {
    private static final long serialVersionUID = 1L;

    /*
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    */
    @Id
    @Column(name = "kdnr", unique = true)
    private Long id;

    @Column(name = "name1")
    private String name1;

    @Column(name = "name2")
    private String name2;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName1() {
        return name1;
    }

    public PmsKunden name1(String name1) {
        this.name1 = name1;
        return this;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public PmsKunden name2(String name2) {
        this.name2 = name2;
        return this;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PmsKunden)) {
            return false;
        }
        return id != null && id.equals(((PmsKunden) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PmsKunden{" +
            "id=" + getId() +
            ", name1='" + getName1() + "'" +
            ", name2='" + getName2() + "'" +
            "}";
    }
}
