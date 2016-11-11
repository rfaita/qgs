package com.qgs.model.cadastro.setor;

import com.qgs.model.endereco.Bairro;
import com.qgs.model.endereco.Cidade;
import com.qgs.model.endereco.Logradouro;
import com.qgs.model.endereco.Pais;
import com.qgs.model.endereco.UF;
import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author rafael
 */
@Entity
public class SetorCriterio implements Serializable {

    @Id
    @SequenceGenerator(name = "seqSetorCriterio", sequenceName = "seqSetorCriterio", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqSetorCriterio")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "idsetor")
    private Setor setor;
    @ManyToOne
    @JoinColumn(name = "idlogradouroCriterio")
    private Logradouro logradouroCriterio;
    @ManyToOne
    @JoinColumn(name = "idbairroCriterio")
    private Bairro bairroCriterio;
    @ManyToOne
    @JoinColumn(name = "idcidadeCriterio")
    private Cidade cidadeCriterio;
    @ManyToOne
    @JoinColumn(name = "idufCriterio")
    private UF ufCriterio;
    @ManyToOne
    @JoinColumn(name = "idpaisCriterio")
    private Pais paisCriterio;

    public Bairro getBairroCriterio() {
        return bairroCriterio;
    }

    public void setBairroCriterio(Bairro bairroCriterio) {
        this.bairroCriterio = bairroCriterio;
    }

    public Cidade getCidadeCriterio() {
        return cidadeCriterio;
    }

    public void setCidadeCriterio(Cidade cidadeCriterio) {
        this.cidadeCriterio = cidadeCriterio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Logradouro getLogradouroCriterio() {
        return logradouroCriterio;
    }

    public void setLogradouroCriterio(Logradouro logradouroCriterio) {
        this.logradouroCriterio = logradouroCriterio;
    }

    public Pais getPaisCriterio() {
        return paisCriterio;
    }

    public void setPaisCriterio(Pais paisCriterio) {
        this.paisCriterio = paisCriterio;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    public UF getUfCriterio() {
        return ufCriterio;
    }

    public void setUfCriterio(UF ufCriterio) {
        this.ufCriterio = ufCriterio;
    }
}
