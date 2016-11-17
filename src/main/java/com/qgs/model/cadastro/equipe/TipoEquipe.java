package com.qgs.model.cadastro.equipe;

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
    @NamedQuery(name = "TipoEquipe.findAll", query = "SELECT o FROM TipoEquipe o")
})
public class TipoEquipe implements Serializable {

    @Id
    @SequenceGenerator(name = "seqTipoEquipe", sequenceName = "seqTipoEquipe", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqTipoEquipe")
    private Integer id;
    private String tipoEquipe;
    private String descricao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoEquipe() {
        return tipoEquipe;
    }

    public void setTipoEquipe(String tipoEquipe) {
        this.tipoEquipe = tipoEquipe;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
