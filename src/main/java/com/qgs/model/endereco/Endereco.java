package com.qgs.model.endereco;

import com.qgs.model.Cliente;
import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author rafael
 */
@Entity
public class Endereco implements Serializable {

    @Id
    @SequenceGenerator(name = "seqendereco", sequenceName = "seqendereco", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqendereco")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "idcliente")
    private Cliente cliente;
    private String numeroImovel;
    @ManyToOne
    @JoinColumn(name = "idlogradouro")
    private Logradouro logradouro;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Logradouro getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(Logradouro logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumeroImovel() {
        return numeroImovel;
    }

    public void setNumeroImovel(String numeroImovel) {
        this.numeroImovel = numeroImovel;
    }
}
