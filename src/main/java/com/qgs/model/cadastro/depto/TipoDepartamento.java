package com.qgs.model.cadastro.depto;

import com.qgs.model.cadastro.servico.TipoServico;
import com.qgs.enums.CriterioSelecaoSetorEnum;
import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author rafael
 */
@Entity
public class TipoDepartamento implements Serializable {

    @Id
    @SequenceGenerator(name = "seqTipoDepartamento", sequenceName = "seqTipoDepartamento", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqTipoDepartamento")
    private Integer id;
    private String tipoDepartamento;
    private CriterioSelecaoSetorEnum criterioSelecaoSetor;
    @ManyToOne
    @JoinColumn(name = "idtiposervicoatendido")
    private TipoServico tipoServicoAtendido;

    public CriterioSelecaoSetorEnum getCriterioSelecaoSetor() {
        return criterioSelecaoSetor;
    }

    public void setCriterioSelecaoSetor(CriterioSelecaoSetorEnum criterioSelecaoSetor) {
        this.criterioSelecaoSetor = criterioSelecaoSetor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoDepartamento() {
        return tipoDepartamento;
    }

    public void setTipoDepartamento(String tipoDepartamento) {
        this.tipoDepartamento = tipoDepartamento;
    }

    public TipoServico getTipoServicoAtendido() {
        return tipoServicoAtendido;
    }

    public void setTipoServicoAtendido(TipoServico tipoServicoAtendido) {
        this.tipoServicoAtendido = tipoServicoAtendido;
    }
}
