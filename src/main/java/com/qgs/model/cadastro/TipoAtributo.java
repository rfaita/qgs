package com.qgs.model.cadastro;

import com.qgs.enums.TipoDadoAtributoEnum;
import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author rafael
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "TipoAtributo.findAll", query = "SELECT p FROM TipoAtributo p")
})
public class TipoAtributo implements Serializable {

    @Id
    @SequenceGenerator(name = "seqtipoatributo", sequenceName = "seqtipoatributo", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqtipoatributo")
    private Integer id;
    private String tipoAtributo;
    private String descricao;
    private TipoDadoAtributoEnum tipoDado;

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

    public String getTipoAtributo() {
        return tipoAtributo;
    }

    public void setTipoAtributo(String tipoAtributo) {
        this.tipoAtributo = tipoAtributo;
    }

    public TipoDadoAtributoEnum getTipoDado() {
        return tipoDado;
    }

    public void setTipoDado(TipoDadoAtributoEnum tipoDado) {
        this.tipoDado = tipoDado;
    }
}
