package com.qgs.model.cadastro;

import com.qgs.model.BaseBean;
import javax.persistence.*;

/**
 *
 * @author rafael
 */
@Entity
public class ValorAtributo extends BaseBean<Long> {

    @Id
    @SequenceGenerator(name = "seqvaloratributo", sequenceName = "seqvaloratributo", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqvaloratributo")
    private Long id;
    private String valorAtributo;
    @ManyToOne
    @JoinColumn(name = "idatributo")
    private Atributo atributo;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValorAtributo() {
        return valorAtributo;
    }

    public void setValorAtributo(String valorAtributo) {
        this.valorAtributo = valorAtributo;
    }

    public Atributo getAtributo() {
        return atributo;
    }

    public void setAtributo(Atributo atributo) {
        this.atributo = atributo;
    }

}
