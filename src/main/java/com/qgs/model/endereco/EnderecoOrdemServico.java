package com.qgs.model.endereco;

import com.qgs.model.ordemservico.OrdemServico;
import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author rafael
 */
@Entity
public class EnderecoOrdemServico implements Serializable {

    @Id
    @SequenceGenerator(name = "seqEnderecoOrdemServico", sequenceName = "seqEnderecoOrdemServico", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqEnderecoOrdemServico")
    private Integer id;
    @OneToOne
    @JoinColumn(name = "idordemservico")
    private OrdemServico ordemServico;
    private String numeroImovel;
    @ManyToOne
    @JoinColumn(name = "idlogradouro")
    private Logradouro logradouro;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrdemServico getOrdemServico() {
        return ordemServico;
    }

    public void setOrdemServico(OrdemServico ordemServico) {
        this.ordemServico = ordemServico;
    }

    public String getNumeroImovel() {
        return numeroImovel;
    }

    public void setNumeroImovel(String numeroImovel) {
        this.numeroImovel = numeroImovel;
    }

    public Logradouro getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(Logradouro logradouro) {
        this.logradouro = logradouro;
    }

}
