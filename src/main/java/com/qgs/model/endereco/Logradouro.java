package com.qgs.model.endereco;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author rafael
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Logradouro.findAll", query = "SELECT o FROM Logradouro o")
    ,
    @NamedQuery(name = "Logradouro.findAllByParam", query = "SELECT o FROM Logradouro o JOIN FETCH o.bairro WHERE o.bairro.id = :idBairro")
})
public class Logradouro implements Serializable {

    @Id
    @SequenceGenerator(name = "seqlogradouro", sequenceName = "seqlogradouro", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqlogradouro")
    private Integer id;
    private String logradouro;
    private String cep;
    @ManyToOne
    @JoinColumn(name = "idbairro")
    private Bairro bairro;

    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }
}
