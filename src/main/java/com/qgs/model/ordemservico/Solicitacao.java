package com.qgs.model.ordemservico;

import com.qgs.enums.StatusSolicitacaoEnum;
import com.qgs.enums.TipoBaixaSolicitacaoEnum;
import com.qgs.model.UsuarioProvedor;
import com.qgs.model.cadastro.Prioridade;
import com.qgs.model.cadastro.servico.Servico;
import com.qgs.model.cadastro.servico.ServicoTramite;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;

/**
 *
 * @author rafael
 */
@Entity
public class Solicitacao implements Serializable {

    @Id
    @SequenceGenerator(name = "seqSolicitacao", sequenceName = "seqSolicitacao", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqSolicitacao")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "idordemservico")
    private OrdemServico ordemServico;
    @ManyToOne
    @JoinColumn(name = "idservico")
    private Servico servico;
    @ManyToOne
    @JoinColumn(name = "idordemservicobaixa")
    private Servico servicoBaixa;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataHoraPrevista;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataHoraPrevistaOriginal;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataHoraAlerta;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataHoraBaixa;
    private TipoBaixaSolicitacaoEnum tipoBaixaSolicitacao;
    @ManyToOne
    @JoinColumn(name = "idprioridade")
    private Prioridade prioridade;
    private String obsAtendimento;
    private String obsComercial;
    private String obsTecnica;
    private StatusSolicitacaoEnum statusSolicitacao;
    @ManyToOne
    @JoinColumn(name = "idusuarioabertura")
    private UsuarioProvedor usuarioAbertura;
    @ManyToOne
    @JoinColumn(name = "idusuarioprogramacao")
    private UsuarioProvedor usuarioProgramacao;
    @ManyToOne
    @JoinColumn(name = "idusuariobaixa")
    private UsuarioProvedor usuarioBaixa;
    @OneToMany(targetEntity = Tramite.class, mappedBy = "solicitacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tramite> tramites;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrdemServico getOrdemServico() {
        return ordemServico;
    }

    public void setOrdemServico(OrdemServico ordemServico) {
        this.ordemServico = ordemServico;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Servico getServicoBaixa() {
        return servicoBaixa;
    }

    public void setServicoBaixa(Servico servicoBaixa) {
        this.servicoBaixa = servicoBaixa;
    }

    public Date getDataHoraPrevista() {
        return dataHoraPrevista;
    }

    public void setDataHoraPrevista(Date dataHoraPrevista) {
        this.dataHoraPrevista = dataHoraPrevista;
    }

    public Date getDataHoraPrevistaOriginal() {
        return dataHoraPrevistaOriginal;
    }

    public void setDataHoraPrevistaOriginal(Date dataHoraPrevistaOriginal) {
        this.dataHoraPrevistaOriginal = dataHoraPrevistaOriginal;
    }

    public Date getDataHoraAlerta() {
        return dataHoraAlerta;
    }

    public void setDataHoraAlerta(Date dataHoraAlerta) {
        this.dataHoraAlerta = dataHoraAlerta;
    }

    public Date getDataHoraBaixa() {
        return dataHoraBaixa;
    }

    public void setDataHoraBaixa(Date dataHoraBaixa) {
        this.dataHoraBaixa = dataHoraBaixa;
    }

    public TipoBaixaSolicitacaoEnum getTipoBaixaSolicitacao() {
        return tipoBaixaSolicitacao;
    }

    public void setTipoBaixaSolicitacao(TipoBaixaSolicitacaoEnum tipoBaixaSolicitacao) {
        this.tipoBaixaSolicitacao = tipoBaixaSolicitacao;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public String getObsAtendimento() {
        return obsAtendimento;
    }

    public void setObsAtendimento(String obsAtendimento) {
        this.obsAtendimento = obsAtendimento;
    }

    public String getObsComercial() {
        return obsComercial;
    }

    public void setObsComercial(String obsComercial) {
        this.obsComercial = obsComercial;
    }

    public String getObsTecnica() {
        return obsTecnica;
    }

    public void setObsTecnica(String obsTecnica) {
        this.obsTecnica = obsTecnica;
    }

    public StatusSolicitacaoEnum getStatusSolicitacao() {
        return statusSolicitacao;
    }

    public void setStatusSolicitacao(StatusSolicitacaoEnum statusSolicitacao) {
        this.statusSolicitacao = statusSolicitacao;
    }

    public UsuarioProvedor getUsuarioAbertura() {
        return usuarioAbertura;
    }

    public void setUsuarioAbertura(UsuarioProvedor usuarioAbertura) {
        this.usuarioAbertura = usuarioAbertura;
    }

    public UsuarioProvedor getUsuarioProgramacao() {
        return usuarioProgramacao;
    }

    public void setUsuarioProgramacao(UsuarioProvedor usuarioProgramacao) {
        this.usuarioProgramacao = usuarioProgramacao;
    }

    public UsuarioProvedor getUsuarioBaixa() {
        return usuarioBaixa;
    }

    public void setUsuarioBaixa(UsuarioProvedor usuarioBaixa) {
        this.usuarioBaixa = usuarioBaixa;
    }

    public List<Tramite> getTramites() {
        return tramites;
    }

    public void setTramites(List<Tramite> tramites) {
        this.tramites = tramites;
    }

}
