package com.qgs.model.cadastro;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author rafael
 */
@Entity
public class ValorAtributo implements Serializable {

    @Id
    @SequenceGenerator(name = "seqvaloratributo", sequenceName = "seqvaloratributo", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqvaloratributo")
    private Integer id;
    private String valorAtributo;
    @ManyToOne
    @JoinColumn(name = "idatributo")
    private Atributo atributo;

    public Atributo getAtributo() {
        return atributo;
    }

    public void setAtributo(Atributo atributo) {
        this.atributo = atributo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValorAtributo() {
        return valorAtributo;
    }

    public void setValorAtributo(String valorAtributo) {
        this.valorAtributo = valorAtributo;
    }
}
