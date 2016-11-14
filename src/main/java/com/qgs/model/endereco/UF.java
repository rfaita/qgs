package com.qgs.model.endereco;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author rafael
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "UF.findAll", query = "SELECT o FROM UF o")
    ,
    @NamedQuery(name = "UF.findAllByParam", query = "SELECT o FROM UF o JOIN FETCH o.pais WHERE o.pais.id = :idPais")
})
public class UF implements Serializable {

    @Id
    @SequenceGenerator(name = "sequf", sequenceName = "sequf", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "sequf")
    private Integer id;
    private String uf;
    private String descricao;
    @ManyToOne
    @JoinColumn(name = "idpais")
    private Pais pais;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}
