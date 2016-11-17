package com.qgs.model.cadastro.servico;

import com.qgs.model.BaseBean;
import com.qgs.model.Empresa;
import com.qgs.model.cadastro.EPI;
import com.qgs.model.cadastro.Prioridade;
import com.qgs.model.cadastro.rubrica.CentroCusto;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

/**
 *
 * @author rafael
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Servico.findAllByParam", query = "SELECT o FROM Servico o "
            + "\n JOIN FETCH o.empresa "
            + "\n JOIN FETCH o.tipoServico "
            + "\n WHERE o.empresa.id = :idEmpresa AND o.ativo = true")
})
public class Servico extends BaseBean<Long> {

    @Id
    @SequenceGenerator(name = "seqservico", sequenceName = "seqservico", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqservico")
    private Long id;
    @NotNull(message = "Serviço é obrigatório.", groups = SaveGroup.class)
    @Size(min = 1, max = 100, message = "O serviço deve estar preenchido e possuir no máximo 100 caractéres.", groups = SaveGroup.class)
    private String servico;
    @Size(max = 4000, message = "A decrição deve possuir no máximo 4000 caractéres.", groups = SaveGroup.class)
    private String descricao;
    @Size(max = 4000, message = "O procedimento deve possuir no máximo 4000 caractéres.", groups = SaveGroup.class)
    private String procedimento;
    @ManyToOne
    @JoinColumn(name = "idgruposervico")
    @NotNull(message = "Grupo serviço é obrigatório.", groups = SaveGroup.class)
    private GrupoServico grupoServico;
    @NotNull(message = "Prazo(Horas) é obrigatório.", groups = SaveGroup.class)
    @Min(value = 1, message = "Prazo(Horas) deve ser maior que zero.", groups = SaveGroup.class)
    private Integer prazoHoras;
    @NotNull(message = "Prazo cliente(Horas) é obrigatório.", groups = SaveGroup.class)
    @Min(value = 1, message = "Prazo cliente(Horas) deve ser maior que zero.", groups = SaveGroup.class)
    private Integer prazoClienteHoras;
    @NotNull(message = "Prazo alerta(Horas) é obrigatório.", groups = SaveGroup.class)
    @Min(value = 1, message = "Prazo alerta(Horas) deve ser maior que zero.", groups = SaveGroup.class)
    private Integer prazoAlertaHoras;
    private Boolean considerarFinalDeSemanaNoPrazo;
    @ManyToOne
    @JoinColumn(name = "idprioridade")
    @NotNull(message = "Prioridade é obrigatório.", groups = SaveGroup.class)
    private Prioridade prioridade;
    private Boolean ativo;
    private Boolean encerramentoAutomatico;
    private Boolean naoAdmiteDuplicacao;
    private Boolean exigirCPF;
    private Boolean exigirRG;
    @ManyToOne
    @JoinColumn(name = "idcentrocusto")
    @NotNull(message = "Centro de custo é obrigatório.", groups = SaveGroup.class)
    private CentroCusto centroCusto;
    @OneToMany(targetEntity = ServicoAtributo.class, mappedBy = "servico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicoAtributo> atributos;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "servicoepi",
            joinColumns = {
                @JoinColumn(name = "idservico")},
            inverseJoinColumns = {
                @JoinColumn(name = "idepi")})
    private List<EPI> epis;
    @OneToMany(targetEntity = ServicoAssociado.class, mappedBy = "servico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicoAssociado> associados;
    @OneToMany(targetEntity = ServicoMaterial.class, mappedBy = "servico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicoMaterial> materiais;
    @OneToMany(targetEntity = ServicoCusto.class, mappedBy = "servico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicoCusto> custos;
    @OneToMany(targetEntity = ServicoTramite.class, mappedBy = "servico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicoTramite> tramites;
    @ManyToOne
    @JoinColumn(name = "idtiposervico")
    @NotNull(message = "Tipo serviço é obrigatório.", groups = SaveGroup.class)
    private TipoServico tipoServico;
    @ManyToOne
    @JoinColumn(name = "idempresa")
    private Empresa empresa;

    public List<ServicoAssociado> getAssociados() {
        return associados;
    }

    public void setAssociados(List<ServicoAssociado> associados) {
        this.associados = associados;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(String procedimento) {
        this.procedimento = procedimento;
    }

    public GrupoServico getGrupoServico() {
        return grupoServico;
    }

    public void setGrupoServico(GrupoServico grupoServico) {
        this.grupoServico = grupoServico;
    }

    public Integer getPrazoHoras() {
        return prazoHoras;
    }

    public void setPrazoHoras(Integer prazoHoras) {
        this.prazoHoras = prazoHoras;
    }

    public Integer getPrazoClienteHoras() {
        return prazoClienteHoras;
    }

    public void setPrazoClienteHoras(Integer prazoClienteHoras) {
        this.prazoClienteHoras = prazoClienteHoras;
    }

    public Integer getPrazoAlertaHoras() {
        return prazoAlertaHoras;
    }

    public void setPrazoAlertaHoras(Integer prazoAlertaHoras) {
        this.prazoAlertaHoras = prazoAlertaHoras;
    }

    public Boolean getConsiderarFinalDeSemanaNoPrazo() {
        return considerarFinalDeSemanaNoPrazo;
    }

    public void setConsiderarFinalDeSemanaNoPrazo(Boolean considerarFinalDeSemanaNoPrazo) {
        this.considerarFinalDeSemanaNoPrazo = considerarFinalDeSemanaNoPrazo;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean getEncerramentoAutomatico() {
        return encerramentoAutomatico;
    }

    public void setEncerramentoAutomatico(Boolean encerramentoAutomatico) {
        this.encerramentoAutomatico = encerramentoAutomatico;
    }

    public Boolean getNaoAdmiteDuplicacao() {
        return naoAdmiteDuplicacao;
    }

    public void setNaoAdmiteDuplicacao(Boolean naoAdmiteDuplicacao) {
        this.naoAdmiteDuplicacao = naoAdmiteDuplicacao;
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

    public CentroCusto getCentroCusto() {
        return centroCusto;
    }

    public void setCentroCusto(CentroCusto centroCusto) {
        this.centroCusto = centroCusto;
    }

    public List<ServicoAtributo> getAtributos() {
        return atributos;
    }

    public void setAtributos(List<ServicoAtributo> atributos) {
        this.atributos = atributos;
    }

    public List<EPI> getEpis() {
        return epis;
    }

    public void setEpis(List<EPI> epis) {
        this.epis = epis;
    }

    public List<ServicoMaterial> getMateriais() {
        return materiais;
    }

    public void setMateriais(List<ServicoMaterial> materiais) {
        this.materiais = materiais;
    }

    public List<ServicoCusto> getCustos() {
        return custos;
    }

    public void setCustos(List<ServicoCusto> custos) {
        this.custos = custos;
    }

    public List<ServicoTramite> getTramites() {
        return tramites;
    }

    public void setTramites(List<ServicoTramite> tramites) {
        this.tramites = tramites;
    }

    public TipoServico getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(TipoServico tipoServico) {
        this.tipoServico = tipoServico;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public interface SaveGroup extends Default {

    }

    public interface CancelGroup extends Default {

    }
}
