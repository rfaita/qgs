package com.qgs.model.cadastro;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author rafael
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Atributo.findAll", query = "SELECT p FROM Atributo p")
})
public class Atributo implements Serializable {

    @Id
    @SequenceGenerator(name = "seqatributo", sequenceName = "seqatributo", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqatributo")
    private Integer id;
    private String atributo;
    private String descricao;
    private Boolean ativo;
    @ManyToOne
    @JoinColumn(name = "idtipoatributo")
    private TipoAtributo tipoAtributo;
    private Integer nrMedicoes;
    @OneToMany(targetEntity = ValorAtributo.class, mappedBy = "atributo")
    private List<ValorAtributo> valoresAtributo;

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getAtributo() {
        return atributo;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

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

    public Integer getNrMedicoes() {
        return nrMedicoes;
    }

    public void setNrMedicoes(Integer nrMedicoes) {
        this.nrMedicoes = nrMedicoes;
    }

    public TipoAtributo getTipoAtributo() {
        return tipoAtributo;
    }

    public void setTipoAtributo(TipoAtributo tipoAtributo) {
        this.tipoAtributo = tipoAtributo;
    }

    public List<ValorAtributo> getValoresAtributo() {
        return valoresAtributo;
    }

    public void setValoresAtributo(List<ValorAtributo> valoresAtributo) {
        this.valoresAtributo = valoresAtributo;
    }
}
