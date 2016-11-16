package com.qgs.model.cadastro.servico;

import com.qgs.enums.CriterioSelecaoLocalidadeEnum;
import com.qgs.model.cadastro.Material;
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
public class ServicoAssociado implements Serializable {

    @Id
    @SequenceGenerator(name = "seqServicoAssociado", sequenceName = "seqServicoAssociado", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqServicoAssociado")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "idservico")
    private Servico servico;
    @ManyToOne
    @JoinColumn(name = "idservicoassociado")
    private Servico servicoAssociado;
    private CriterioSelecaoLocalidadeEnum criterioSelecaoLocalidade;

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

    public Servico getServicoAssociado() {
        return servicoAssociado;
    }

    public void setServicoAssociado(Servico servicoAssociado) {
        this.servicoAssociado = servicoAssociado;
    }

    public CriterioSelecaoLocalidadeEnum getCriterioSelecaoLocalidade() {
        return criterioSelecaoLocalidade;
    }

    public void setCriterioSelecaoLocalidade(CriterioSelecaoLocalidadeEnum criterioSelecaoLocalidade) {
        this.criterioSelecaoLocalidade = criterioSelecaoLocalidade;
    }

}
