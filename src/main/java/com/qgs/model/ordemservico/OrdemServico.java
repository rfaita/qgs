package com.qgs.model.ordemservico;

import com.qgs.enums.StatusOrdemServicoEnum;
import com.qgs.model.BaseBean;
import com.qgs.model.Cliente;
import com.qgs.model.endereco.EnderecoOrdemServico;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.groups.Default;

/**
 *
 * @author rafael
 */
@Entity
public class OrdemServico extends BaseBean<Long> {

    @Id
    @SequenceGenerator(name = "seqOrdemServico", sequenceName = "seqOrdemServico", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqOrdemServico")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "idcliente")
    private Cliente cliente;
    @OneToMany(targetEntity = Solicitacao.class, mappedBy = "ordemServico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Solicitacao> solicitacoes;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataHoraAbertura;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataHoraEncerramento;
    private String obs;
    private String obsInterna;
    @OneToOne(targetEntity = EnderecoOrdemServico.class, mappedBy = "ordemServico")
    private EnderecoOrdemServico enderecoOrdemServicoAbertura;
    private String nomeSolicitante;
    private String cpfSolicitante;
    private String rgSolicitante;
    private Boolean possuiCobranca;
    private StatusOrdemServicoEnum statusOrdemServico;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Solicitacao> getSolicitacoes() {
        return solicitacoes;
    }

    public void setSolicitacoes(List<Solicitacao> solicitacoes) {
        this.solicitacoes = solicitacoes;
    }

    public Date getDataHoraAbertura() {
        return dataHoraAbertura;
    }

    public void setDataHoraAbertura(Date dataHoraAbertura) {
        this.dataHoraAbertura = dataHoraAbertura;
    }

    public Date getDataHoraEncerramento() {
        return dataHoraEncerramento;
    }

    public void setDataHoraEncerramento(Date dataHoraEncerramento) {
        this.dataHoraEncerramento = dataHoraEncerramento;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getObsInterna() {
        return obsInterna;
    }

    public void setObsInterna(String obsInterna) {
        this.obsInterna = obsInterna;
    }

    public EnderecoOrdemServico getEnderecoOrdemServicoAbertura() {
        return enderecoOrdemServicoAbertura;
    }

    public void setEnderecoOrdemServicoAbertura(EnderecoOrdemServico enderecoOrdemServicoAbertura) {
        this.enderecoOrdemServicoAbertura = enderecoOrdemServicoAbertura;
    }

    public String getNomeSolicitante() {
        return nomeSolicitante;
    }

    public void setNomeSolicitante(String nomeSolicitante) {
        this.nomeSolicitante = nomeSolicitante;
    }

    public String getCpfSolicitante() {
        return cpfSolicitante;
    }

    public void setCpfSolicitante(String cpfSolicitante) {
        this.cpfSolicitante = cpfSolicitante;
    }

    public String getRgSolicitante() {
        return rgSolicitante;
    }

    public void setRgSolicitante(String rgSolicitante) {
        this.rgSolicitante = rgSolicitante;
    }

    public Boolean getPossuiCobranca() {
        return possuiCobranca;
    }

    public void setPossuiCobranca(Boolean possuiCobranca) {
        this.possuiCobranca = possuiCobranca;
    }

    public StatusOrdemServicoEnum getStatusOrdemServico() {
        return statusOrdemServico;
    }

    public void setStatusOrdemServico(StatusOrdemServicoEnum statusOrdemServico) {
        this.statusOrdemServico = statusOrdemServico;
    }

    public interface SaveGroup extends Default {

    }

    public interface CancelGroup extends Default {

    }
}
