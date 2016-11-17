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
public class FormaAtendimento extends BaseBean<Integer> {

    @Id
    @SequenceGenerator(name = "seqFormaAtendimento", sequenceName = "seqFormaAtendimento", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqFormaAtendimento")
    private Integer id;
    @NotNull(message = "Forma de atendimento é obrigatório.", groups = Servico.SaveGroup.class)
    @Size(min = 1, max = 100, message = "A forma de atendimento deve estar preenchido e possuir no máximo 100 caractéres.", groups = Servico.SaveGroup.class)
    private String formaAtendimento;
    @Size(max = 4000, message = "A descrição deve possuir no máximo 4000 caractéres.", groups = Servico.SaveGroup.class)
    private String descricao;
    @ManyToOne
    @JoinColumn(name = "idempresa")
    private Empresa empresa;
    private Boolean ativo;

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFormaAtendimento() {
        return formaAtendimento;
    }

    public void setFormaAtendimento(String formaAtendimento) {
        this.formaAtendimento = formaAtendimento;
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

    public interface SaveGroup extends Default {

    }

    public interface CancelGroup extends Default {

    }

}
