package com.qgs.model.cadastro.depto;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author rafael
 */
@Entity
public class Departamento implements Serializable {

    @Id
    @SequenceGenerator(name = "seqDepartamento", sequenceName = "seqDepartamento", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqDepartamento")
    private Integer id;
    private Integer departamento;
    private Boolean ativo;
    @ManyToOne
    @JoinColumn(name = "idtipodepartamento")
    private TipoDepartamento tipoDepartamento;

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Integer getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Integer departamento) {
        this.departamento = departamento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoDepartamento getTipoDepartamento() {
        return tipoDepartamento;
    }

    public void setTipoDepartamento(TipoDepartamento tipoDepartamento) {
        this.tipoDepartamento = tipoDepartamento;
    }
}
