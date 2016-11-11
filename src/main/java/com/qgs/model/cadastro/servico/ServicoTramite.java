package com.qgs.model.cadastro.servico;

import com.qgs.model.cadastro.depto.TipoDepartamento;
import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author rafael
 */
@Entity
public class ServicoTramite implements Serializable {

    @Id
    @SequenceGenerator(name = "seqServicoTramite", sequenceName = "seqServicoTramite", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqServicoTramite")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "idservico")
    private Servico servico;
    private Boolean permiteEncerrar;
    private Boolean enviarMobile;
    private Boolean tramiteInicial;
    @ManyToOne
    @JoinColumn(name = "idtipoDepartamentoTramiteInicial")
    private TipoDepartamento tipoDepartamentoTramiteInicial;

    public Boolean getEnviarMobile() {
        return enviarMobile;
    }

    public void setEnviarMobile(Boolean enviarMobile) {
        this.enviarMobile = enviarMobile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getPermiteEncerrar() {
        return permiteEncerrar;
    }

    public void setPermiteEncerrar(Boolean permiteEncerrar) {
        this.permiteEncerrar = permiteEncerrar;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public TipoDepartamento getTipoDepartamentoTramiteInicial() {
        return tipoDepartamentoTramiteInicial;
    }

    public void setTipoDepartamentoTramiteInicial(TipoDepartamento tipoDepartamentoTramiteInicial) {
        this.tipoDepartamentoTramiteInicial = tipoDepartamentoTramiteInicial;
    }

    public Boolean getTramiteInicial() {
        return tramiteInicial;
    }

    public void setTramiteInicial(Boolean tramiteInicial) {
        this.tramiteInicial = tramiteInicial;
    }
}
