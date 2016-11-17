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
    private Long id;
    @ManyToOne
    @JoinColumn(name = "idservico")
    private Servico servico;
    private Boolean permiteEncerrar;
    private Boolean enviarAplicativoMovel;
    private Boolean tramiteInicial;
    @ManyToOne
    @JoinColumn(name = "idtipoDepartamentoTramiteInicial")
    private TipoDepartamento tipoDepartamentoTramiteInicial;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Boolean getPermiteEncerrar() {
        return permiteEncerrar;
    }

    public void setPermiteEncerrar(Boolean permiteEncerrar) {
        this.permiteEncerrar = permiteEncerrar;
    }

    public Boolean getEnviarAplicativoMovel() {
        return enviarAplicativoMovel;
    }

    public void setEnviarAplicativoMovel(Boolean enviarAplicativoMovel) {
        this.enviarAplicativoMovel = enviarAplicativoMovel;
    }

    public Boolean getTramiteInicial() {
        return tramiteInicial;
    }

    public void setTramiteInicial(Boolean tramiteInicial) {
        this.tramiteInicial = tramiteInicial;
    }

    public TipoDepartamento getTipoDepartamentoTramiteInicial() {
        return tipoDepartamentoTramiteInicial;
    }

    public void setTipoDepartamentoTramiteInicial(TipoDepartamento tipoDepartamentoTramiteInicial) {
        this.tipoDepartamentoTramiteInicial = tipoDepartamentoTramiteInicial;
    }

}
