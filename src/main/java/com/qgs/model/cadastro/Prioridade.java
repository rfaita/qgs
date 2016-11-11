package com.qgs.model.cadastro;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author rafael
 */
@Entity
public class Prioridade implements Serializable {

    @Id
    @SequenceGenerator(name = "seqprioridade", sequenceName = "seqprioridade", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqprioridade")
    private Integer id;
    private String prioridade;
    private Boolean ativo;

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }
}
