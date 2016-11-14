package com.qgs.model.cadastro.depto;

import com.qgs.model.cadastro.servico.TipoServico;
import com.qgs.enums.CriterioSelecaoSetorEnum;
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
@NamedQueries({
    @NamedQuery(name = "TipoDepartamento.findAllByParam", query = "SELECT o FROM TipoDepartamento o JOIN FETCH o.empresa WHERE o.empresa.id = :idEmpresa AND o.ativo = true")
})
public class TipoDepartamento extends BaseBean<Integer> {

    @Id
    @SequenceGenerator(name = "seqTipoDepartamento", sequenceName = "seqTipoDepartamento", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqTipoDepartamento")
    private Integer id;
    @NotNull(message = "Tipo de departamento é obrigatório.", groups = SaveGroup.class)
    @Size(min = 1, max = 50, message = "O tipo de departamento deve estar preenchido e possuir no máximo 50 caractéres.", groups = SaveGroup.class)
    private String tipoDepartamento;
    @NotNull(message = "O critério de seleção de setor é obrigatório.", groups = SaveGroup.class)
    private CriterioSelecaoSetorEnum criterioSelecaoSetor;
    @ManyToOne
    @JoinColumn(name = "idtiposervicoatendido")
    @NotNull(message = "O tipo de serviço atendido é obrigatório.", groups = SaveGroup.class)
    private TipoServico tipoServicoAtendido;
    @ManyToOne
    @JoinColumn(name = "idEmpresa")
    private Empresa empresa;
    private Boolean ativo;

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public CriterioSelecaoSetorEnum getCriterioSelecaoSetor() {
        return criterioSelecaoSetor;
    }

    public void setCriterioSelecaoSetor(CriterioSelecaoSetorEnum criterioSelecaoSetor) {
        this.criterioSelecaoSetor = criterioSelecaoSetor;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoDepartamento() {
        return tipoDepartamento;
    }

    public void setTipoDepartamento(String tipoDepartamento) {
        this.tipoDepartamento = tipoDepartamento;
    }

    public TipoServico getTipoServicoAtendido() {
        return tipoServicoAtendido;
    }

    public void setTipoServicoAtendido(TipoServico tipoServicoAtendido) {
        this.tipoServicoAtendido = tipoServicoAtendido;
    }

    public interface SaveGroup extends Default {

    }

    public interface CancelGroup extends Default {

    }
}
