package com.qgs.model.cadastro.servico;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author rafael
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "TipoServico.findAll", query = "SELECT o FROM TipoServico o")
})
public class TipoServico implements Serializable {

    @Id
    @SequenceGenerator(name = "seqTipoServico", sequenceName = "seqTipoServico", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqTipoServico")
    private Integer id;
    private String tipoServico;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }
}
