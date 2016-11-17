package com.qgs.model.cadastro.equipe;

import com.qgs.model.BaseBean;
import com.qgs.model.Empresa;
import com.qgs.model.cadastro.depto.Departamento;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

/**
 *
 * @author rafael
 */
@Entity
public class Equipe extends BaseBean<Long> {

    @Id
    @SequenceGenerator(name = "seqEquipe", sequenceName = "seqEquipe", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqEquipe")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "iddepartamento")
    @NotNull(message = "Departamento é obrigatório.", groups = SaveGroup.class)
    private Departamento departamento;
    @NotNull(message = "Equipe é obrigatório.", groups = SaveGroup.class)
    @Size(min = 1, max = 100, message = "A equipe deve estar preenchida e possuir no máximo 100 caractéres.", groups = SaveGroup.class)
    private String equipe;
    private Boolean ativo;
    @ManyToOne
    @JoinColumn(name = "idtipoequipe")
    @NotNull(message = "Tipo equipe é obrigatório.", groups = SaveGroup.class)
    private TipoEquipe tipoEquipe;
    @ManyToOne
    @JoinColumn(name = "idempresa")
    private Empresa empresa;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public String getEquipe() {
        return equipe;
    }

    public void setEquipe(String equipe) {
        this.equipe = equipe;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public TipoEquipe getTipoEquipe() {
        return tipoEquipe;
    }

    public void setTipoEquipe(TipoEquipe tipoEquipe) {
        this.tipoEquipe = tipoEquipe;
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
