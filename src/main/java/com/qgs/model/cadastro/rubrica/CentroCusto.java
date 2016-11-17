package com.qgs.model.cadastro.rubrica;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author rafael
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "CentroCusto.findAll", query = "SELECT o FROM CentroCusto o WHERE o.ativo = true")
})
public class CentroCusto implements Serializable {

    @Id
    @SequenceGenerator(name = "seqcentrocusto", sequenceName = "seqcentrocusto", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqcentrocusto")
    private Integer id;
    private String centroCusto;
    private Boolean ativo;

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getCentroCusto() {
        return centroCusto;
    }

    public void setCentroCusto(String centroCusto) {
        this.centroCusto = centroCusto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
