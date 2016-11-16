package com.qgs.model.cadastro.servico;

import com.qgs.model.cadastro.Atributo;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author rafael
 */
@Entity
public class ServicoAtributo implements Serializable {

    @Id
    @SequenceGenerator(name = "seqServicoAtributo", sequenceName = "seqServicoAtributo", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqServicoAtributo")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "idservico")
    private Servico servico;
    @ManyToOne
    @JoinColumn(name = "idAtributo")
    private Atributo atributo;
    private Boolean obrigatorio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Atributo getAtributo() {
        return atributo;
    }

    public void setAtributo(Atributo atributo) {
        this.atributo = atributo;
    }

    public Boolean getObrigatorio() {
        return obrigatorio;
    }

    public void setObrigatorio(Boolean obrigatorio) {
        this.obrigatorio = obrigatorio;
    }

}
