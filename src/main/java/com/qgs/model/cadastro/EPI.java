package com.qgs.model.cadastro;

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
public class EPI extends BaseBean<Integer> {

    @Id
    @SequenceGenerator(name = "seqepi", sequenceName = "seqepi", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqepi")
    private Integer id;
    @NotNull(message = "EPI é obrigatório.", groups = SaveGroup.class)
    @Size(min = 1, max = 50, message = "O EPI deve estar preenchido e possuir no máximo 50 caractéres.", groups = SaveGroup.class)
    private String epi;
    @NotNull(message = "Descrição do EPI é obrigatório.", groups = SaveGroup.class)
    @Size(min = 1, max = 4000, message = "A descrição do EPI deve estar preenchido e possuir no máximo 4000 caractéres.", groups = SaveGroup.class)
    private String descricao;
    private Boolean ativo;
    @ManyToOne
    @JoinColumn(name = "idempresa")
    private Empresa empresa;

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEpi() {
        return epi;
    }

    public void setEpi(String epi) {
        this.epi = epi;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
