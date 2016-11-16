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
    @NamedQuery(name = "TipoRubrica.findAll", query = "SELECT o FROM TipoRubrica o")
})
public class TipoRubrica implements Serializable {

    @Id
    @SequenceGenerator(name = "seqtiporubrica", sequenceName = "seqtiporubrica", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqtiporubrica")
    private Integer id;
    private String tipoRubrica;
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoRubrica() {
        return tipoRubrica;
    }

    public void setTipoRubrica(String tipoRubrica) {
        this.tipoRubrica = tipoRubrica;
    }
}
