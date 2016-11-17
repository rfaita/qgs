package com.qgs.model.ordemservico;

import com.qgs.model.BaseBean;
import com.qgs.model.cadastro.depto.Departamento;
import com.qgs.model.cadastro.equipe.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author rafael
 */
@Entity
public class Tramite extends BaseBean<Long> {

    @Id
    @SequenceGenerator(name = "seqTramite", sequenceName = "seqTramite", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqTramite")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "idsolicitacao")
    private Solicitacao solicitacao;
    @ManyToOne
    @JoinColumn(name = "iddepartamentoatual")
    private Departamento departamentoAtual;
    @ManyToOne
    @JoinColumn(name = "iddepartamentodestino")
    private Departamento departamentoDestino;
    @ManyToOne
    @JoinColumn(name = "idequipe")
    private Equipe equipe;
    @ManyToOne
    @JoinColumn(name = "idequipeexecucao")
    private Equipe equipeExecucao;
    private String obsTramite;
    private Boolean enviadoAplicativoMovel;
    private Boolean retornadoAplicativoMovel;
    private Boolean estornadoAplicativoMovel;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Solicitacao getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(Solicitacao solicitacao) {
        this.solicitacao = solicitacao;
    }

    public Departamento getDepartamentoAtual() {
        return departamentoAtual;
    }

    public void setDepartamentoAtual(Departamento departamentoAtual) {
        this.departamentoAtual = departamentoAtual;
    }

    public Departamento getDepartamentoDestino() {
        return departamentoDestino;
    }

    public void setDepartamentoDestino(Departamento departamentoDestino) {
        this.departamentoDestino = departamentoDestino;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public Equipe getEquipeExecucao() {
        return equipeExecucao;
    }

    public void setEquipeExecucao(Equipe equipeExecucao) {
        this.equipeExecucao = equipeExecucao;
    }

    public String getObsTramite() {
        return obsTramite;
    }

    public void setObsTramite(String obsTramite) {
        this.obsTramite = obsTramite;
    }

    public Boolean getEnviadoAplicativoMovel() {
        return enviadoAplicativoMovel;
    }

    public void setEnviadoAplicativoMovel(Boolean enviadoAplicativoMovel) {
        this.enviadoAplicativoMovel = enviadoAplicativoMovel;
    }

    public Boolean getRetornadoAplicativoMovel() {
        return retornadoAplicativoMovel;
    }

    public void setRetornadoAplicativoMovel(Boolean retornadoAplicativoMovel) {
        this.retornadoAplicativoMovel = retornadoAplicativoMovel;
    }

    public Boolean getEstornadoAplicativoMovel() {
        return estornadoAplicativoMovel;
    }

    public void setEstornadoAplicativoMovel(Boolean estornadoAplicativoMovel) {
        this.estornadoAplicativoMovel = estornadoAplicativoMovel;
    }

}
