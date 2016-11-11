package com.qgs.model.cadastro.servico;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author rafael
 */
@Entity
public class GrupoServico implements Serializable {

    @Id
    @SequenceGenerator(name = "seqgruposervico", sequenceName = "seqgruposervico", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqgruposervico")
    private Integer id;
    private String grupoServico;
    private String descricao;
    private Boolean ativo;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getGrupoServico() {
        return grupoServico;
    }

    public void setGrupoServico(String grupoServico) {
        this.grupoServico = grupoServico;
    }

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
}
