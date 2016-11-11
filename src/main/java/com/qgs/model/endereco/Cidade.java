package com.qgs.model.endereco;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author rafael
 */
@Entity
public class Cidade implements Serializable {

    @Id
    @SequenceGenerator(name = "seqcidade", sequenceName = "seqcidade", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqcidade")
    private Integer id;
    private String cidade;
    @ManyToOne
    @JoinColumn(name = "idpais")
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
