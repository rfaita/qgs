package com.qgs.ui.window.cadastro.servico;

import com.qgs.model.cadastro.Atributo;
import com.qgs.model.cadastro.EPI;
import com.qgs.model.cadastro.Prioridade;
import com.qgs.model.cadastro.servico.Servico;
import com.qgs.model.cadastro.servico.ServicoAtributo;
import com.qgs.model.cadastro.servico.TipoServico;
import com.qgs.service.ListAllService;
import com.qgs.service.cadastro.servico.ServicoService;
import com.qgs.service.secutiry.SecurityUtils;
import com.qgs.ui.QGSUI;
import com.qgs.ui.view.cadastro.servico.ServicoView;
import com.qgs.ui.window.base.BaseWindow;
import com.qgs.util.validation.Validation;
import com.vaadin.data.Property;
import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author rafael
 */
@RequestScoped
public class ServicoWindow extends BaseWindow<Long, Servico> {

    private TextField txtServico;
    private TextField txtPrazoHoras;
    private TextField txtPrazoClienteHoras;
    private TextField txtPrazoAlertaHoras;
    private TextArea txtDescricao;
    private CheckBox ckAtivo;
    private CheckBox ckNaoAdmiteDuplicacao;
    private CheckBox ckExigeCPF;
    private CheckBox ckExigeRG;
    private CheckBox ckConsiderarFinalDeSemanaNoPrazo;
    private BeanContainer<Integer, TipoServico> bcTipoServico;
    private ComboBox cmbTipoServico;
    private BeanContainer<Integer, Prioridade> bcPrioridade;
    private ComboBox cmbPrioridade;

    private BeanContainer<Integer, EPI> bcEPI;
    private ComboBox cmbEPI;
    private Button btAddEPI;
    private Button btDelEPI;
    private BeanContainer<Integer, EPI> bcEPIServico;
    private Table tbEPIServico;

    private BeanContainer<Integer, Atributo> bcAtributo;
    private ComboBox cmbAtributo;
    private CheckBox ckAtributoObrigatorio;
    private Button btAddAtributo;
    private Button btDelAtributo;
    private BeanContainer<Long, ServicoAtributo> bcServicoAtributo;
    private Table tbServicoAtributo;
    private Long indexServicoAtributo = -1L;

    @EJB
    private ServicoService servicoService;
    @EJB
    private ListAllService listAllService;
    @EJB
    private SecurityUtils securityUtils;

    public ServicoWindow() {
        super("servicowindow", "Gestão de Serviço");

        TabSheet tabSheet = new TabSheet();
        tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
        tabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);

        FormLayout content = new FormLayout();
        content.setSizeFull();
        content.setMargin(new MarginInfo(true));
        content.setSpacing(true);

        content.addComponent(getTxtId());
        content.addComponent(getTxtServico());
        content.addComponent(getTxtDescricao());
        content.addComponent(getCmbTipoServico());
        content.addComponent(getCmbPrioridade());
        content.addComponent(getTxtPrazoHoras());
        content.addComponent(getTxtPrazoClienteHoras());
        content.addComponent(getTxtPrazoAlertaHoras());
        content.addComponent(getCkConsiderarFinalDeSemanaNoPrazo());
        content.addComponent(getCkNaoAdmiteDuplicacao());

        HorizontalLayout h = new HorizontalLayout(getCkExigeRG(), getCkExigeCPF());
        h.setWidth(100, Unit.PERCENTAGE);

        content.addComponent(h);
        content.addComponent(getCkAtivo());

        tabSheet.addTab(content, "Dados básicos");
        tabSheet.addTab(getAbaEPI(), "EPI");
        tabSheet.addTab(getAbaAtributo(), "Atributos");

        VerticalLayout vl = new VerticalLayout(tabSheet, getBtSave());

        vl.setComponentAlignment(getBtSave(), Alignment.BOTTOM_RIGHT);
        vl.setMargin(new MarginInfo(true));
        vl.setSpacing(true);

        setContent(vl);

    }

    private FormLayout getAbaEPI() {
        FormLayout content = new FormLayout();
        content.setSizeFull();
        content.setMargin(new MarginInfo(true));
        content.setSpacing(true);

        content.addComponent(getCmbEPI());

        HorizontalLayout h = new HorizontalLayout(getBtAddEPI(), getBtDelEPI());
        h.setWidth(100, Unit.PERCENTAGE);

        content.addComponent(h);
        content.addComponent(getTbEPIServico());

        return content;
    }

    private FormLayout getAbaAtributo() {
        FormLayout content = new FormLayout();
        content.setSizeFull();
        content.setMargin(new MarginInfo(true));
        content.setSpacing(true);

        content.addComponent(getCmbAtributo());
        content.addComponent(getCkAtributoObrigatorio());

        HorizontalLayout h = new HorizontalLayout(getBtAddAtributo(), getBtDelAtributo());
        h.setWidth(100, Unit.PERCENTAGE);

        content.addComponent(h);
        content.addComponent(getTbServicoAtributo());

        return content;
    }

    @Override
    protected void doLoadDados(Long id) {
        if (id != null && id > 0) {
            setDado(servicoService.findCompleteById(id));

            getTxtId().setValue(getDado().getId() + "");
            getTxtPrazoAlertaHoras().setValue(getDado().getPrazoAlertaHoras() + "");
            getTxtPrazoClienteHoras().setValue(getDado().getPrazoClienteHoras() + "");
            getTxtPrazoHoras().setValue(getDado().getPrazoHoras() + "");
            getTxtDescricao().setValue(getDado().getDescricao());
            if (getDado().getTipoServico() != null) {
                getCmbTipoServico().setValue(getDado().getTipoServico().getId());
            }
            if (getDado().getPrioridade() != null) {
                getCmbPrioridade().setValue(getDado().getPrioridade().getId());
            }
            getTxtServico().setValue(getDado().getServico());
            getCkAtivo().setValue(getDado().getAtivo());
            getCkConsiderarFinalDeSemanaNoPrazo().setValue(getDado().getConsiderarFinalDeSemanaNoPrazo());
            getCkExigeRG().setValue(getDado().getExigirRG());
            getCkExigeCPF().setValue(getDado().getExigirCPF());
            getCkNaoAdmiteDuplicacao().setValue(getDado().getNaoAdmiteDuplicacao());
            getBcEPIServico().addAll(getDado().getEpis());
            getBcServicoAtributo().addAll(getDado().getAtributos());
        }
    }

    private TextField getTxtServico() {
        if (txtServico == null) {
            txtServico = new TextField("Servico");
            txtServico.setWidth(100, Unit.PERCENTAGE);
            txtServico.setRequired(true);
        }
        return txtServico;
    }

    private TextField getTxtPrazoHoras() {
        if (txtPrazoHoras == null) {
            txtPrazoHoras = new TextField("Prazo(Horas)");
            txtPrazoHoras.setWidth(100, Unit.PERCENTAGE);
            txtPrazoHoras.addValidator(new RegexpValidator("[1-9]{1}[0-9]*", "Prazo deve ser um número inteiro e positivo."));
        }
        return txtPrazoHoras;
    }

    private TextField getTxtPrazoClienteHoras() {
        if (txtPrazoClienteHoras == null) {
            txtPrazoClienteHoras = new TextField("Prazo Cliente(Horas)");
            txtPrazoClienteHoras.setWidth(100, Unit.PERCENTAGE);
            txtPrazoClienteHoras.addValidator(new RegexpValidator("[1-9]{1}[0-9]*", "Prazo cliente deve ser um número inteiro e positivo."));
        }
        return txtPrazoClienteHoras;
    }

    private TextField getTxtPrazoAlertaHoras() {
        if (txtPrazoAlertaHoras == null) {
            txtPrazoAlertaHoras = new TextField("Prazo Alerta(Horas)");
            txtPrazoAlertaHoras.setWidth(100, Unit.PERCENTAGE);
            txtPrazoAlertaHoras.addValidator(new RegexpValidator("[1-9]{1}[0-9]*", "Prazo alerta deve ser um número inteiro e positivo."));
        }
        return txtPrazoAlertaHoras;
    }

    private TextArea getTxtDescricao() {
        if (txtDescricao == null) {
            txtDescricao = new TextArea("Descrição");
            txtDescricao.setWidth(100, Unit.PERCENTAGE);
            txtDescricao.setNullRepresentation("");
        }
        return txtDescricao;
    }

    private BeanContainer<Integer, TipoServico> getBcTipoServico() {
        if (bcTipoServico == null) {
            bcTipoServico = new BeanContainer<Integer, TipoServico>(TipoServico.class);
            bcTipoServico.setBeanIdResolver((TipoServico bean) -> bean.getId());
        }
        return bcTipoServico;
    }

    private BeanContainer<Integer, Prioridade> getBcPrioridade() {
        if (bcPrioridade == null) {
            bcPrioridade = new BeanContainer<Integer, Prioridade>(Prioridade.class);
            bcPrioridade.setBeanIdResolver((Prioridade bean) -> bean.getId());
        }
        return bcPrioridade;
    }

    private ComboBox getCmbTipoServico() {
        if (cmbTipoServico == null) {
            cmbTipoServico = new ComboBox("Tipo serviço");
            cmbTipoServico.setInputPrompt("Informe o tipo...");
            cmbTipoServico.setContainerDataSource(getBcTipoServico());
            cmbTipoServico.setWidth(100, Unit.PERCENTAGE);
            cmbTipoServico.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
            cmbTipoServico.setItemCaptionPropertyId("tipoServico");
            cmbTipoServico.setImmediate(true);
            cmbTipoServico.setRequired(true);
        }
        return cmbTipoServico;
    }

    private ComboBox getCmbPrioridade() {
        if (cmbPrioridade == null) {
            cmbPrioridade = new ComboBox("Prioridade");
            cmbPrioridade.setInputPrompt("Informe a prioridade...");
            cmbPrioridade.setContainerDataSource(getBcPrioridade());
            cmbPrioridade.setWidth(100, Unit.PERCENTAGE);
            cmbPrioridade.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
            cmbPrioridade.setItemCaptionPropertyId("prioridade");
            cmbPrioridade.setImmediate(true);
            cmbPrioridade.setRequired(true);
        }
        return cmbPrioridade;
    }

    private CheckBox getCkAtivo() {
        if (ckAtivo == null) {
            ckAtivo = new CheckBox("Ativo?");
        }
        return ckAtivo;
    }

    private CheckBox getCkNaoAdmiteDuplicacao() {
        if (ckNaoAdmiteDuplicacao == null) {
            ckNaoAdmiteDuplicacao = new CheckBox("Não admite duplicação?");
        }
        return ckNaoAdmiteDuplicacao;
    }

    private CheckBox getCkExigeRG() {
        if (ckExigeRG == null) {
            ckExigeRG = new CheckBox("Exige RG?");
        }
        return ckExigeRG;
    }

    private CheckBox getCkExigeCPF() {
        if (ckExigeCPF == null) {
            ckExigeCPF = new CheckBox("Exige CPF?");
        }
        return ckExigeCPF;
    }

    private CheckBox getCkConsiderarFinalDeSemanaNoPrazo() {
        if (ckConsiderarFinalDeSemanaNoPrazo == null) {
            ckConsiderarFinalDeSemanaNoPrazo = new CheckBox("Considerar final de semana?");
        }
        return ckConsiderarFinalDeSemanaNoPrazo;
    }

    private Button getBtAddEPI() {
        if (btAddEPI == null) {
            btAddEPI = new Button("Adicionar EPI");
            btAddEPI.setIcon(FontAwesome.PLUS);
            btAddEPI.addClickListener((Button.ClickEvent event) -> {
                if (getCmbEPI().getValue() == null) {
                    QGSUI.showWarn("Informe o EPI.");
                    return;
                }
                getBcEPIServico().addBean(getBcEPI().getItem(getCmbEPI().getValue()).getBean());
                getCmbEPI().setValue(null);

            });
        }
        return btAddEPI;
    }

    private Button getBtDelEPI() {
        if (btDelEPI == null) {
            btDelEPI = new Button("Remover EPI");
            btDelEPI.setIcon(FontAwesome.MINUS);
            btDelEPI.addClickListener((Button.ClickEvent event) -> {
                if (getTbEPIServico().getValue() != null) {
                    EPI o = getBcEPIServico().getItem(getTbEPIServico().getValue()).getBean();
                    getBcEPIServico().removeItem(o.getId());
                } else {
                    QGSUI.showWarn("Selecione o que deseja remover.");
                }

            });
        }
        return btDelEPI;
    }

    private BeanContainer<Integer, EPI> getBcEPIServico() {
        if (bcEPIServico == null) {
            bcEPIServico = new BeanContainer<Integer, EPI>(EPI.class);
            bcEPIServico.setBeanIdResolver((EPI bean) -> bean.getId());
        }
        return bcEPIServico;
    }

    private BeanContainer<Integer, EPI> getBcEPI() {
        if (bcEPI == null) {
            bcEPI = new BeanContainer<Integer, EPI>(EPI.class);
            bcEPI.setBeanIdResolver((EPI bean) -> bean.getId());
        }
        return bcEPI;
    }

    private ComboBox getCmbEPI() {
        if (cmbEPI == null) {
            cmbEPI = new ComboBox("EPI");
            cmbEPI.setInputPrompt("Informe o epi...");
            cmbEPI.setContainerDataSource(getBcEPI());
            cmbEPI.setWidth(100, Unit.PERCENTAGE);
            cmbEPI.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
            cmbEPI.setItemCaptionPropertyId("epi");
            cmbEPI.setImmediate(true);
        }
        return cmbEPI;
    }

    private Table getTbEPIServico() {
        if (tbEPIServico == null) {
            tbEPIServico = new Table();
            tbEPIServico.setWidth(100, Unit.PERCENTAGE);
            tbEPIServico.setHeight(300, Unit.PIXELS);
            tbEPIServico.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES);
            tbEPIServico.addStyleName(ValoTheme.TABLE_COMPACT);
            tbEPIServico.setSelectable(true);

            tbEPIServico.setColumnCollapsingAllowed(true);
            tbEPIServico.setColumnReorderingAllowed(true);
            tbEPIServico.setContainerDataSource(getBcEPIServico());
            tbEPIServico.setSortAscending(false);

            tbEPIServico.setVisibleColumns("epi");
            tbEPIServico.setColumnExpandRatio("epi", 1);
            tbEPIServico.setColumnHeaders("EPI");

            tbEPIServico.setImmediate(true);
        }

        return tbEPIServico;
    }

    private Button getBtAddAtributo() {
        if (btAddAtributo == null) {
            btAddAtributo = new Button("Adicionar Atributo");
            btAddAtributo.setIcon(FontAwesome.PLUS);
            btAddAtributo.addClickListener((Button.ClickEvent event) -> {
                if (getCmbAtributo().getValue() == null) {
                    QGSUI.showWarn("Informe o atributo.");
                    return;
                }
                ServicoAtributo sa = new ServicoAtributo();
                sa.setAtributo(getBcAtributo().getItem(getCmbAtributo().getValue()).getBean());
                sa.setObrigatorio(getCkAtributoObrigatorio().getValue());
                sa.setId(indexServicoAtributo--);
                getBcServicoAtributo().addBean(sa);
                getCmbAtributo().setValue(null);
                getCkAtributoObrigatorio().setValue(Boolean.FALSE);

            });
        }
        return btAddAtributo;
    }

    private Button getBtDelAtributo() {
        if (btDelAtributo == null) {
            btDelAtributo = new Button("Remover atributo");
            btDelAtributo.setIcon(FontAwesome.MINUS);
            btDelAtributo.addClickListener((Button.ClickEvent event) -> {
                if (getTbServicoAtributo().getValue() != null) {
                    ServicoAtributo o = getBcServicoAtributo().getItem(getTbServicoAtributo().getValue()).getBean();
                    getBcServicoAtributo().removeItem(o.getId());
                } else {
                    QGSUI.showWarn("Selecione o que deseja remover.");
                }

            });
        }
        return btDelAtributo;
    }

    private BeanContainer<Long, ServicoAtributo> getBcServicoAtributo() {
        if (bcServicoAtributo == null) {
            bcServicoAtributo = new BeanContainer<Long, ServicoAtributo>(ServicoAtributo.class);
            bcServicoAtributo.setBeanIdResolver((ServicoAtributo bean) -> bean.getId());
        }
        return bcServicoAtributo;
    }

    private BeanContainer<Integer, Atributo> getBcAtributo() {
        if (bcAtributo == null) {
            bcAtributo = new BeanContainer<Integer, Atributo>(Atributo.class);
            bcAtributo.setBeanIdResolver((Atributo bean) -> bean.getId());
        }
        return bcAtributo;
    }

    private ComboBox getCmbAtributo() {
        if (cmbAtributo == null) {
            cmbAtributo = new ComboBox("Atributo");
            cmbAtributo.setInputPrompt("Informe o atributo...");
            cmbAtributo.setContainerDataSource(getBcAtributo());
            cmbAtributo.setWidth(100, Unit.PERCENTAGE);
            cmbAtributo.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
            cmbAtributo.setItemCaptionPropertyId("atributo");
            cmbAtributo.setImmediate(true);
        }
        return cmbAtributo;
    }

    private CheckBox getCkAtributoObrigatorio() {
        if (ckAtributoObrigatorio == null) {
            ckAtributoObrigatorio = new CheckBox("Obrigatório?");
        }
        return ckAtributoObrigatorio;
    }

    private Table getTbServicoAtributo() {
        if (tbServicoAtributo == null) {
            tbServicoAtributo = new Table();
            tbServicoAtributo.setWidth(100, Unit.PERCENTAGE);
            tbServicoAtributo.setHeight(300, Unit.PIXELS);
            tbServicoAtributo.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES);
            tbServicoAtributo.addStyleName(ValoTheme.TABLE_COMPACT);
            tbServicoAtributo.setSelectable(true);

            tbServicoAtributo.setColumnCollapsingAllowed(true);
            tbServicoAtributo.setColumnReorderingAllowed(true);
            tbServicoAtributo.setContainerDataSource(getBcServicoAtributo());
            tbServicoAtributo.setSortAscending(false);

            tbServicoAtributo.addGeneratedColumn("atributo", (Table source, Object itemId, Object columnId) -> {
                Property<Atributo> prop = source.getItem(itemId).getItemProperty(columnId);
                if (prop.getType().equals(Atributo.class)) {
                    return prop.getValue().getAtributo();
                }
                return "";
            });

            tbServicoAtributo.setVisibleColumns("atributo", "obrigatorio");
            tbServicoAtributo.setColumnExpandRatio("atributo", 1);
            tbServicoAtributo.setColumnHeaders("Atributo", "Obrigatório");

            tbServicoAtributo.setImmediate(true);
        }

        return tbServicoAtributo;
    }

    @Override
    protected void doSave() {

        try {

            getTxtPrazoHoras().validate();
            getTxtPrazoClienteHoras().validate();
            getTxtPrazoAlertaHoras().validate();

        } catch (Validator.InvalidValueException ex) {
            QGSUI.doCatch(ex);
            return;
        }

        if (getDado() == null) {
            setDado(new Servico());
        }
        if (getTxtId().getValue() != null && !getTxtId().getValue().isEmpty()) {
            getDado().setId(Long.parseLong(getTxtId().getValue()));
        }
        getDado().setServico(getTxtServico().getValue());
        getDado().setDescricao(getTxtDescricao().getValue());
        getDado().setAtivo(getCkAtivo().getValue());
        getDado().setExigirRG(getCkExigeRG().getValue());
        getDado().setExigirCPF(getCkExigeCPF().getValue());
        getDado().setNaoAdmiteDuplicacao(getCkNaoAdmiteDuplicacao().getValue());
        getDado().setConsiderarFinalDeSemanaNoPrazo(getCkConsiderarFinalDeSemanaNoPrazo().getValue());
        getDado().setPrazoAlertaHoras(Validation.validInteger(getTxtPrazoAlertaHoras().getValue()));
        getDado().setPrazoClienteHoras(Validation.validInteger(getTxtPrazoClienteHoras().getValue()));
        getDado().setPrazoHoras(Validation.validInteger(getTxtPrazoHoras().getValue()));
        if (getCmbTipoServico().getValue() != null) {
            getDado().setTipoServico(getBcTipoServico().getItem(getCmbTipoServico().getValue()).getBean());
        } else {
            getDado().setTipoServico(null);
        }
        if (getCmbPrioridade().getValue() != null) {
            getDado().setPrioridade(getBcPrioridade().getItem(getCmbPrioridade().getValue()).getBean());
        } else {
            getDado().setPrioridade(null);
        }

        getDado().setEpis(new ArrayList<EPI>());
        for (Integer id : getBcEPIServico().getItemIds()) {
            getDado().getEpis().add(getBcEPIServico().getItem(id).getBean());
        }
        getDado().setAtributos(new ArrayList<ServicoAtributo>());
        for (Long id : getBcServicoAtributo().getItemIds()) {
            ServicoAtributo sa = getBcServicoAtributo().getItem(id).getBean();
            if (sa.getId() != null && sa.getId() < 0) {
                sa.setId(null);
                sa.setServico(getDado());
            }
            getDado().getAtributos().add(sa);
        }

        try {

            servicoService.save(getDado());
            QGSUI.showInfo("Dados salvos com sucesso.");
            sendRefresh();
            close();

        } catch (Exception ex) {
            QGSUI.doCatch(ex);
        }
    }

    @Override
    protected void doInitiate() {
        getBcTipoServico().addAll(listAllService.findAll(TipoServico.class));
        getBcPrioridade().addAll(listAllService.findAll(Prioridade.class));
        getBcEPI().addAll(listAllService.findAllByParam(EPI.class, "idEmpresa", securityUtils.getEmpresa().getId()));
        getBcAtributo().addAll(listAllService.findAllByParam(Atributo.class, "idEmpresa", securityUtils.getEmpresa().getId()));
    }

    @Override
    protected Class<ServicoView> getSendRefreshClazz() {
        return ServicoView.class;
    }

}
