package com.qgs.model.cadastro.setor;

import com.qgs.model.BaseBean;
import com.qgs.model.Empresa;
import com.qgs.model.cadastro.EPI;
import com.qgs.model.cadastro.servico.TipoServico;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

/**
 *
 * @author rafael
 */
@Entity
public class Setor extends BaseBean<Integer> {

    @Id
    @SequenceGenerator(name = "seqSetor", sequenceName = "seqSetor", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqSetor")
    private Integer id;
    @NotNull(message = "Setor é obrigatório.", groups = EPI.SaveGroup.class)
    @Size(min = 1, max = 50, message = "O setor deve estar preenchido e possuir no máximo 50 caractéres.", groups = EPI.SaveGroup.class)
    private String setor;
    private Boolean ativo;
    @ManyToOne
    @JoinColumn(name = "idtiposervicoatendido")
    private TipoServico tipoServicoAtendido;
    @OneToMany(targetEntity = SetorCriterio.class, mappedBy = "setor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SetorCriterio> criterios;
    @ManyToOne
    @JoinColumn(name = "idempresa")
    private Empresa empresa;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public TipoServico getTipoServicoAtendido() {
        return tipoServicoAtendido;
    }

    public void setTipoServicoAtendido(TipoServico tipoServicoAtendido) {
        this.tipoServicoAtendido = tipoServicoAtendido;
    }

    public List<SetorCriterio> getCriterios() {
        return criterios;
    }

    public void setCriterios(List<SetorCriterio> criterios) {
        this.criterios = criterios;
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
