package com.qgs.model.endereco;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author rafael
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Cidade.findAll", query = "SELECT o FROM Cidade o")
    ,
    @NamedQuery(name = "Cidade.findAllByParam", query = "SELECT o FROM Cidade o JOIN FETCH o.uf WHERE o.uf.id = :idUf")

})
public class Cidade implements Serializable {

    @Id
    @SequenceGenerator(name = "seqcidade", sequenceName = "seqcidade", initialValue = 10000, allocationSize = 100)
    @GeneratedValue(generator = "seqcidade")
    private Integer id;
    private String cidade;
    @ManyToOne
    @JoinColumn(name = "idestado")
    private UF uf;

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UF getUf() {
        return uf;
    }

    public void setUf(UF uf) {
        this.uf = uf;
    }
}
