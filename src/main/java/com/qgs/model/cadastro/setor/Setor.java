package com.qgs.model.cadastro.setor;

import com.qgs.model.cadastro.servico.TipoServico;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author rafael
 */
@Entity
public class Setor implements Serializable {

    @Id
    @SequenceGenerator(name = "seqSetor", sequenceName = "seqSetor", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqSetor")
    private Integer id;
    private String setor;
    @ManyToOne
    @JoinColumn(name = "idtiposervicoatendido")
    private TipoServico tipoServicoAtendido;
    @OneToMany(targetEntity = SetorCriterio.class, mappedBy = "setor")
    private List<SetorCriterio> criterios;

    public List<SetorCriterio> getCriterios() {
        return criterios;
    }

    public void setCriterios(List<SetorCriterio> criterios) {
        this.criterios = criterios;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public TipoServico getTipoServicoAtendido() {
        return tipoServicoAtendido;
    }

    public void setTipoServicoAtendido(TipoServico tipoServicoAtendido) {
        this.tipoServicoAtendido = tipoServicoAtendido;
    }

}
