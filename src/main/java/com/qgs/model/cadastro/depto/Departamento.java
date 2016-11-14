package com.qgs.model.cadastro.depto;

import com.qgs.model.BaseBean;
import com.qgs.model.Empresa;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

/**
 *
 * @author rafael
 */
@Entity
public class Departamento extends BaseBean<Integer> {

    @Id
    @SequenceGenerator(name = "seqDepartamento", sequenceName = "seqDepartamento", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqDepartamento")
    private Integer id;
    @NotNull(message = "Departamento é obrigatório.", groups = SaveGroup.class)
    @Size(min = 1, max = 50, message = "O departamento deve estar preenchido e possuir no máximo 50 caractéres.", groups = SaveGroup.class)
    private String departamento;
    private Boolean ativo;
    @ManyToOne
    @JoinColumn(name = "idtipodepartamento")
    @NotNull(message = "O tipo de departamento é obrigatório.", groups = SaveGroup.class)
    private TipoDepartamento tipoDepartamento;
    @ManyToOne
    @JoinColumn(name = "idEmpresa")
    private Empresa empresa;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public TipoDepartamento getTipoDepartamento() {
        return tipoDepartamento;
    }

    public void setTipoDepartamento(TipoDepartamento tipoDepartamento) {
        this.tipoDepartamento = tipoDepartamento;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public interface SaveGroup extends Default {

    }

    public interface CancelGroup extends Default {

    }
}
