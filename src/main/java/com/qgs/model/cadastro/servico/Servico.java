package com.qgs.model.cadastro.servico;

import com.qgs.model.cadastro.Atributo;
import com.qgs.model.cadastro.EPI;
import com.qgs.model.cadastro.Material;
import com.qgs.model.cadastro.Prioridade;
import com.qgs.model.cadastro.rubrica.CentroCusto;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author rafael
 */
@Entity
public class Servico implements Serializable {

    @Id
    @SequenceGenerator(name = "seqservico", sequenceName = "seqservico", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqservico")
    private Integer id;
    private String servico;
    @ManyToOne
    @JoinColumn(name = "idgruposervico")
    private GrupoServico grupoServico;
    private Integer prazoHoras;
    private Integer prazoClienteHoras;
    private Integer prazoAlertaHoras;
    private Boolean considerarFinalDeSemanaNoPrazo;
    @ManyToOne
    @JoinColumn(name = "idprioridade")
    private Prioridade prioridade;
    private Boolean ativo;
    private Boolean encerramentoAutomatico;
    private Boolean naoAdmiteDuplicacao;
    private Boolean exigirCPF;
    private Boolean exigirRG;
    @ManyToOne
    @JoinColumn(name = "idcentrocusto")
    private CentroCusto centroCusto;
    private Boolean enviarAplicativoMovel;
    @ManyToMany
    @JoinTable(name = "servicoatributo",
    joinColumns = {
        @JoinColumn(name = "idatributo")},
    inverseJoinColumns = {
        @JoinColumn(name = "idservico")})
    private List<Atributo> atributos;
    @ManyToMany
    @JoinTable(name = "servicoepi",
    joinColumns = {
        @JoinColumn(name = "idepi")},
    inverseJoinColumns = {
        @JoinColumn(name = "idservico")})
    private List<EPI> epis;
    @ManyToMany
    @JoinTable(name = "servicomaterial",
    joinColumns = {
        @JoinColumn(name = "idmaterial")},
    inverseJoinColumns = {
        @JoinColumn(name = "idservico")})
    private List<Material> materiais;
    @OneToMany(targetEntity = ServicoCusto.class, mappedBy = "servico")
    private List<ServicoCusto> custos;
    @OneToMany(targetEntity = ServicoTramite.class, mappedBy = "servico")
    private List<ServicoTramite> tramites;
    @ManyToOne
    @JoinColumn(name = "idtiposervico")
    private TipoServico tipoServico;

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public List<Atributo> getAtributos() {
        return atributos;
    }

    public void setAtributos(List<Atributo> atributos) {
        this.atributos = atributos;
    }

    public CentroCusto getCentroCusto() {
        return centroCusto;
    }

    public void setCentroCusto(CentroCusto centroCusto) {
        this.centroCusto = centroCusto;
    }

    public Boolean getConsiderarFinalDeSemanaNoPrazo() {
        return considerarFinalDeSemanaNoPrazo;
    }

    public void setConsiderarFinalDeSemanaNoPrazo(Boolean considerarFinalDeSemanaNoPrazo) {
        this.considerarFinalDeSemanaNoPrazo = considerarFinalDeSemanaNoPrazo;
    }

    public List<ServicoCusto> getCustos() {
        return custos;
    }

    public void setCustos(List<ServicoCusto> custos) {
        this.custos = custos;
    }

    public Boolean getEncerramentoAutomatico() {
        return encerramentoAutomatico;
    }

    public void setEncerramentoAutomatico(Boolean encerramentoAutomatico) {
        this.encerramentoAutomatico = encerramentoAutomatico;
    }

    public Boolean getEnviarAplicativoMovel() {
        return enviarAplicativoMovel;
    }

    public void setEnviarAplicativoMovel(Boolean enviarAplicativoMovel) {
        this.enviarAplicativoMovel = enviarAplicativoMovel;
    }

    public List<EPI> getEpis() {
        return epis;
    }

    public void setEpis(List<EPI> epis) {
        this.epis = epis;
    }

    public Boolean getExigirCPF() {
        return exigirCPF;
    }

    public void setExigirCPF(Boolean exigirCPF) {
        this.exigirCPF = exigirCPF;
    }

    public Boolean getExigirRG() {
        return exigirRG;
    }

    public void setExigirRG(Boolean exigirRG) {
        this.exigirRG = exigirRG;
    }

    public GrupoServico getGrupoServico() {
        return grupoServico;
    }

    public void setGrupoServico(GrupoServico grupoServico) {
        this.grupoServico = grupoServico;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Material> getMateriais() {
        return materiais;
    }

    public void setMateriais(List<Material> materiais) {
        this.materiais = materiais;
    }

    public Boolean getNaoAdmiteDuplicacao() {
        return naoAdmiteDuplicacao;
    }

    public void setNaoAdmiteDuplicacao(Boolean naoAdmiteDuplicacao) {
        this.naoAdmiteDuplicacao = naoAdmiteDuplicacao;
    }

    public Integer getPrazoAlertaHoras() {
        return prazoAlertaHoras;
    }

    public void setPrazoAlertaHoras(Integer prazoAlertaHoras) {
        this.prazoAlertaHoras = prazoAlertaHoras;
    }

    public Integer getPrazoClienteHoras() {
        return prazoClienteHoras;
    }

    public void setPrazoClienteHoras(Integer prazoClienteHoras) {
        this.prazoClienteHoras = prazoClienteHoras;
    }

    public Integer getPrazoHoras() {
        return prazoHoras;
    }

    public void setPrazoHoras(Integer prazoHoras) {
        this.prazoHoras = prazoHoras;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public TipoServico getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(TipoServico tipoServico) {
        this.tipoServico = tipoServico;
    }

    public List<ServicoTramite> getTramites() {
        return tramites;
    }

    public void setTramites(List<ServicoTramite> tramites) {
        this.tramites = tramites;
    }
}
