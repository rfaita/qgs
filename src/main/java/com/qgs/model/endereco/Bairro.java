package com.qgs.model.endereco;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author rafael
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Bairro.findAll", query = "SELECT o FROM Bairro o")
    ,
    @NamedQuery(name = "Bairro.findAllByParam", query = "SELECT o FROM Bairro o JOIN FETCH o.cidade WHERE o.cidade.id = :idCidade")
})
public class Bairro implements Serializable {

    @Id
    @SequenceGenerator(name = "seqbairro", sequenceName = "seqbairro", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqbairro")
    private Integer id;
    private String bairro;
    @ManyToOne
    @JoinColumn(name = "idlogradouroinicial")
    private Logradouro logradouroInicial;
    @ManyToOne
    @JoinColumn(name = "idlogradourofinal")
    private Logradouro logradouroFinal;
    @ManyToOne
    @JoinColumn(name = "idcidade")
    private Cidade cidade;

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Logradouro getLogradouroFinal() {
        return logradouroFinal;
    }

    public void setLogradouroFinal(Logradouro logradouroFinal) {
        this.logradouroFinal = logradouroFinal;
    }

    public Logradouro getLogradouroInicial() {
        return logradouroInicial;
    }

    public void setLogradouroInicial(Logradouro logradouroInicial) {
        this.logradouroInicial = logradouroInicial;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
}
