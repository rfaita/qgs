package com.qgs.model.ordemservico;

import com.qgs.model.BaseBean;
import com.qgs.model.Empresa;
import com.qgs.model.cadastro.servico.Servico;
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
public class OrigemAtendimento extends BaseBean<Integer> {

    @Id
    @SequenceGenerator(name = "seqOrigemAtendimento", sequenceName = "seqOrigemAtendimento", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqOrigemAtendimento")
    private Integer id;
    @NotNull(message = "Origem de atendimento é obrigatório.", groups = Servico.SaveGroup.class)
    @Size(min = 1, max = 100, message = "A origem de atendimento deve estar preenchido e possuir no máximo 100 caractéres.", groups = Servico.SaveGroup.class)
    private String origemAtendimento;
    @Size(max = 4000, message = "A descrição deve possuir no máximo 4000 caractéres.", groups = Servico.SaveGroup.class)
    private String descricao;
    @ManyToOne
    @JoinColumn(name = "idempresa")
    private Empresa empresa;
    private Boolean ativo;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrigemAtendimento() {
        return origemAtendimento;
    }

    public void setOrigemAtendimento(String origemAtendimento) {
        this.origemAtendimento = origemAtendimento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public interface SaveGroup extends Default {

    }

    public interface CancelGroup extends Default {

    }

}
