package com.qgs.model.cadastro;

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
    @NamedQuery(name = "TipoMaterial.findAll", query = "SELECT o FROM TipoMaterial o")
})
public class TipoMaterial implements Serializable {

    @Id
    @SequenceGenerator(name = "seqtipomaterial", sequenceName = "seqtipomaterial", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqtipomaterial")
    private Integer id;
    private String tipoMaterial;
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

    public String getTipoMaterial() {
        return tipoMaterial;
    }

    public void setTipoMaterial(String tipoMaterial) {
        this.tipoMaterial = tipoMaterial;
    }
}
