package com.qgs.model.endereco;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author rafael
 */
@Entity
public class Pais implements Serializable {

    @Id
    @SequenceGenerator(name = "seqpais", sequenceName = "seqpais", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqpais")
    private Integer id;
    private String pais;
    private String abrev;

    public String getAbrev() {
        return abrev;
    }

    public void setAbrev(String abrev) {
        this.abrev = abrev;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
