package com.qgs.ui.window.cadastro.servico;

import com.qgs.enums.CriterioSelecaoLocalidadeEnum;
import com.qgs.enums.TipoCustomEnum;
import com.qgs.model.cadastro.Atributo;
import com.qgs.model.cadastro.EPI;
import com.qgs.model.cadastro.Material;
import com.qgs.model.cadastro.Prioridade;
import com.qgs.model.cadastro.rubrica.Rubrica;
import com.qgs.model.cadastro.servico.Servico;
import com.qgs.model.cadastro.servico.ServicoAssociado;
import com.qgs.model.cadastro.servico.ServicoAtributo;
import com.qgs.model.cadastro.servico.ServicoCusto;
import com.qgs.model.cadastro.servico.ServicoMaterial;
import com.qgs.model.cadastro.servico.TipoServico;
import com.qgs.service.ListAllService;
import com.qgs.service.cadastro.servico.ServicoService;
import com.qgs.service.secutiry.SecurityUtils;
import com.qgs.ui.QGSUI;
import com.qgs.ui.util.validator.DoubleValidator;
import com.qgs.ui.util.validator.PositiveIntegerValidator;
import com.qgs.ui.view.cadastro.servico.ServicoView;
import com.qgs.ui.window.base.BaseWindow;
import com.qgs.util.validation.Validation;
import com.vaadin.data.Property;
import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ArrayList;
import java.util.Arrays;
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

    private BeanContainer<Integer, Material> bcMaterial;
    private ComboBox cmbMaterial;
    private TextField txtQtdMaterial;
    private Button btAddMaterial;
    private Button btDelMaterial;
    private BeanContainer<Long, ServicoMaterial> bcServicoMaterial;
    private Table tbServicoMaterial;
    private Long indexServicoMaterial = -1L;

    private BeanContainer<Long, Servico> bcServico;
    private ComboBox cmbServico;
    private BeanContainer<String, CriterioSelecaoLocalidadeEnum> bcCriterioSelecaoLocalidade;
    private ComboBox cmbCriterioSelecaoLocalidade;
    private Button btAddServicoAssociado;
    private Button btDelServicoAssociado;
    private BeanContainer<Long, ServicoAssociado> bcServicoAssociado;
    private Table tbServicoAssociado;
    private Long indexServicoAssociado = -1L;

    private BeanContainer<Integer, Rubrica> bcRubrica;
    private ComboBox cmbRubrica;
    private TextField txtQtdParcela;
    private TextField txtValor;
    private BeanContainer<String, TipoCustomEnum> bcTipoCusto;
    private OptionGroup ogTipoCusto;
    private Button btAddRubrica;
    private Button btDelRubrica;
    private BeanContainer<Long, ServicoCusto> bcServicoCusto;
    private Table tbServicoCusto;
    private Long indexServicoCusto = -1L;

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
        tabSheet.addTab(getAbaMaterial(), "Materiais");
        tabSheet.addTab(getAbaAssociado(), "Serviços Associados");
        tabSheet.addTab(getAbaCusto(), "Serviço Custo");

        VerticalLayout vl = new VerticalLayout(tabSheet, getBtSave());

        vl.setComponentAlignment(getBtSave(), Alignment.BOTTOM_RIGHT);
        vl.setMargin(new MarginInfo(true));
        vl.setSpacing(true);

        setWidth(900, Unit.PIXELS);

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

    private FormLayout getAbaMaterial() {
        FormLayout content = new FormLayout();
        content.setSizeFull();
        content.setMargin(new MarginInfo(true));
        content.setSpacing(true);

        content.addComponent(getCmbMaterial());
        content.addComponent(getTxtQtdMaterial());

        HorizontalLayout h = new HorizontalLayout(getBtAddMaterial(), getBtDelMaterial());
        h.setWidth(100, Unit.PERCENTAGE);

        content.addComponent(h);
        content.addComponent(getTbServicoMaterial());

        return content;
    }

    private FormLayout getAbaAssociado() {
        FormLayout content = new FormLayout();
        content.setSizeFull();
        content.setMargin(new MarginInfo(true));
        content.setSpacing(true);

        content.addComponent(getCmbServico());
        content.addComponent(getCmbCriterioSelecaoLocalidade());

        HorizontalLayout h = new HorizontalLayout(getBtAddServicoAssociado(), getBtDelServicoAssociado());
        h.setWidth(100, Unit.PERCENTAGE);

        content.addComponent(h);
        content.addComponent(getTbServicoAssociado());

        return content;
    }

    private FormLayout getAbaCusto() {
        FormLayout content = new FormLayout();
        content.setSizeFull();
        content.setMargin(new MarginInfo(true));
        content.setSpacing(true);

        content.addComponent(getCmbRubrica());
        content.addComponent(getOgTipoCusto());
        content.addComponent(getTxtQtdParcela());
        content.addComponent(getTxtValor());

        HorizontalLayout h = new HorizontalLayout(getBtAddRubrica(), getBtDelRubrica());
        h.setWidth(100, Unit.PERCENTAGE);

        content.addComponent(h);
        content.addComponent(getTbServicoCusto());

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
            getBcServicoMaterial().addAll(getDado().getMateriais());
            getBcServicoAssociado().addAll(getDado().getAssociados());
            getBcServicoCusto().addAll(getDado().getCustos());
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
            txtPrazoHoras.addValidator(new PositiveIntegerValidator("Prazo deve ser um número inteiro e positivo."));
            txtPrazoHoras.setValue("0");
        }
        return txtPrazoHoras;
    }

    private TextField getTxtPrazoClienteHoras() {
        if (txtPrazoClienteHoras == null) {
            txtPrazoClienteHoras = new TextField("Prazo Cliente(Horas)");
            txtPrazoClienteHoras.setWidth(100, Unit.PERCENTAGE);
            txtPrazoClienteHoras.addValidator(new PositiveIntegerValidator("Prazo cliente deve ser um número inteiro e positivo."));
            txtPrazoClienteHoras.setValue("0");
        }
        return txtPrazoClienteHoras;
    }

    private TextField getTxtPrazoAlertaHoras() {
        if (txtPrazoAlertaHoras == null) {
            txtPrazoAlertaHoras = new TextField("Prazo Alerta(Horas)");
            txtPrazoAlertaHoras.setWidth(100, Unit.PERCENTAGE);
            txtPrazoAlertaHoras.addValidator(new PositiveIntegerValidator("Prazo alerta deve ser um número inteiro e positivo."));
            txtPrazoAlertaHoras.setValue("0");
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
            btAddEPI = new Button("Adicionar");
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
            btDelEPI = new Button("Remover");
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
            btAddAtributo = new Button("Adicionar");
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
            btDelAtributo = new Button("Remover");
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

    private Button getBtAddMaterial() {
        if (btAddMaterial == null) {
            btAddMaterial = new Button("Adicionar");
            btAddMaterial.setIcon(FontAwesome.PLUS);
            btAddMaterial.addClickListener((Button.ClickEvent event) -> {
                if (getCmbMaterial().getValue() == null) {
                    QGSUI.showWarn("Informe o material.");
                    return;
                }
                if (getTxtQtdMaterial().getValue() == null || getTxtQtdMaterial().getValue().isEmpty()) {
                    QGSUI.showWarn("Informe a quantidade do material.");
                    return;
                }
                try {
                    getTxtQtdMaterial().validate();
                } catch (Validator.InvalidValueException ex) {
                    QGSUI.doCatch(ex);
                    return;
                }

                ServicoMaterial sa = new ServicoMaterial();
                sa.setMaterial(getBcMaterial().getItem(getCmbMaterial().getValue()).getBean());
                sa.setQtd(Validation.validInteger(getTxtQtdMaterial().getValue()));
                sa.setId(indexServicoMaterial--);
                getBcServicoMaterial().addBean(sa);
                getCmbMaterial().setValue(null);
                getTxtQtdMaterial().setValue("0");

            });
        }
        return btAddMaterial;
    }

    private Button getBtDelMaterial() {
        if (btDelMaterial == null) {
            btDelMaterial = new Button("Remover");
            btDelMaterial.setIcon(FontAwesome.MINUS);
            btDelMaterial.addClickListener((Button.ClickEvent event) -> {
                if (getTbServicoMaterial().getValue() != null) {
                    ServicoMaterial o = getBcServicoMaterial().getItem(getTbServicoMaterial().getValue()).getBean();
                    getBcServicoMaterial().removeItem(o.getId());
                } else {
                    QGSUI.showWarn("Selecione o que deseja remover.");
                }

            });
        }
        return btDelMaterial;
    }

    private BeanContainer<Long, ServicoMaterial> getBcServicoMaterial() {
        if (bcServicoMaterial == null) {
            bcServicoMaterial = new BeanContainer<Long, ServicoMaterial>(ServicoMaterial.class);
            bcServicoMaterial.setBeanIdResolver((ServicoMaterial bean) -> bean.getId());
        }
        return bcServicoMaterial;
    }

    private BeanContainer<Integer, Material> getBcMaterial() {
        if (bcMaterial == null) {
            bcMaterial = new BeanContainer<Integer, Material>(Material.class);
            bcMaterial.setBeanIdResolver((Material bean) -> bean.getId());
        }
        return bcMaterial;
    }

    private ComboBox getCmbMaterial() {
        if (cmbMaterial == null) {
            cmbMaterial = new ComboBox("Material");
            cmbMaterial.setInputPrompt("Informe o material...");
            cmbMaterial.setContainerDataSource(getBcMaterial());
            cmbMaterial.setWidth(100, Unit.PERCENTAGE);
            cmbMaterial.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
            cmbMaterial.setItemCaptionPropertyId("material");
            cmbMaterial.setImmediate(true);
        }
        return cmbMaterial;
    }

    private TextField getTxtQtdMaterial() {
        if (txtQtdMaterial == null) {
            txtQtdMaterial = new TextField("Qtd.");
            txtQtdMaterial.setWidth(100, Unit.PERCENTAGE);
            txtQtdMaterial.addValidator(new PositiveIntegerValidator("Qtd. Material deve ser um número inteiro e positivo."));
            txtQtdMaterial.setValue("0");
        }
        return txtQtdMaterial;
    }

    private Table getTbServicoMaterial() {
        if (tbServicoMaterial == null) {
            tbServicoMaterial = new Table();
            tbServicoMaterial.setWidth(100, Unit.PERCENTAGE);
            tbServicoMaterial.setHeight(300, Unit.PIXELS);
            tbServicoMaterial.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES);
            tbServicoMaterial.addStyleName(ValoTheme.TABLE_COMPACT);
            tbServicoMaterial.setSelectable(true);

            tbServicoMaterial.setColumnCollapsingAllowed(true);
            tbServicoMaterial.setColumnReorderingAllowed(true);
            tbServicoMaterial.setContainerDataSource(getBcServicoMaterial());
            tbServicoMaterial.setSortAscending(false);

            tbServicoMaterial.addGeneratedColumn("material", (Table source, Object itemId, Object columnId) -> {
                Property<Material> prop = source.getItem(itemId).getItemProperty(columnId);
                if (prop.getType().equals(Material.class)) {
                    return prop.getValue().getMaterial();
                }
                return "";
            });

            tbServicoMaterial.setVisibleColumns("material", "qtd");
            tbServicoMaterial.setColumnExpandRatio("material", 1);
            tbServicoMaterial.setColumnHeaders("Material", "Quantidade");

            tbServicoMaterial.setImmediate(true);
        }

        return tbServicoMaterial;
    }

    private Button getBtAddServicoAssociado() {
        if (btAddServicoAssociado == null) {
            btAddServicoAssociado = new Button("Adicionar");
            btAddServicoAssociado.setIcon(FontAwesome.PLUS);
            btAddServicoAssociado.addClickListener((Button.ClickEvent event) -> {
                if (getCmbServico().getValue() == null) {
                    QGSUI.showWarn("Informe o serviço.");
                    return;
                }
                if (getCmbCriterioSelecaoLocalidade().getValue() == null) {
                    QGSUI.showWarn("Informe o critério de localidade.");
                    return;
                }

                ServicoAssociado sa = new ServicoAssociado();
                sa.setServicoAssociado(getBcServico().getItem(getCmbServico().getValue()).getBean());
                sa.setCriterioSelecaoLocalidade(getBcCriterioSelecaoLocalidade().getItem(getCmbCriterioSelecaoLocalidade().getValue()).getBean());
                sa.setId(indexServicoAssociado--);
                getBcServicoAssociado().addBean(sa);
                getCmbServico().setValue(null);
                getCmbCriterioSelecaoLocalidade().setValue(null);

            });
        }
        return btAddServicoAssociado;
    }

    private Button getBtDelServicoAssociado() {
        if (btDelServicoAssociado == null) {
            btDelServicoAssociado = new Button("Remover");
            btDelServicoAssociado.setIcon(FontAwesome.MINUS);
            btDelServicoAssociado.addClickListener((Button.ClickEvent event) -> {
                if (getTbServicoAssociado().getValue() != null) {
                    ServicoAssociado o = getBcServicoAssociado().getItem(getTbServicoAssociado().getValue()).getBean();
                    getBcServicoAssociado().removeItem(o.getId());
                } else {
                    QGSUI.showWarn("Selecione o que deseja remover.");
                }

            });
        }
        return btDelServicoAssociado;
    }

    private BeanContainer<Long, ServicoAssociado> getBcServicoAssociado() {
        if (bcServicoAssociado == null) {
            bcServicoAssociado = new BeanContainer<Long, ServicoAssociado>(ServicoAssociado.class);
            bcServicoAssociado.setBeanIdResolver((ServicoAssociado bean) -> bean.getId());
        }
        return bcServicoAssociado;
    }

    private BeanContainer<Long, Servico> getBcServico() {
        if (bcServico == null) {
            bcServico = new BeanContainer<Long, Servico>(Servico.class);
            bcServico.setBeanIdResolver((Servico bean) -> bean.getId());
        }
        return bcServico;
    }

    private BeanContainer<String, CriterioSelecaoLocalidadeEnum> getBcCriterioSelecaoLocalidade() {
        if (bcCriterioSelecaoLocalidade == null) {
            bcCriterioSelecaoLocalidade = new BeanContainer<String, CriterioSelecaoLocalidadeEnum>(CriterioSelecaoLocalidadeEnum.class);
            bcCriterioSelecaoLocalidade.setBeanIdResolver((CriterioSelecaoLocalidadeEnum bean) -> bean.getCriterio());
        }
        return bcCriterioSelecaoLocalidade;
    }

    private ComboBox getCmbServico() {
        if (cmbServico == null) {
            cmbServico = new ComboBox("Serviço");
            cmbServico.setInputPrompt("Informe o serviço...");
            cmbServico.setContainerDataSource(getBcServico());
            cmbServico.setWidth(100, Unit.PERCENTAGE);
            cmbServico.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
            cmbServico.setItemCaptionPropertyId("servico");
            cmbServico.setImmediate(true);
        }
        return cmbServico;
    }

    private ComboBox getCmbCriterioSelecaoLocalidade() {
        if (cmbCriterioSelecaoLocalidade == null) {
            cmbCriterioSelecaoLocalidade = new ComboBox("Critério Localidade");
            cmbCriterioSelecaoLocalidade.setInputPrompt("Informe o critério...");
            cmbCriterioSelecaoLocalidade.setContainerDataSource(getBcCriterioSelecaoLocalidade());
            cmbCriterioSelecaoLocalidade.setWidth(100, Unit.PERCENTAGE);
            cmbCriterioSelecaoLocalidade.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
            cmbCriterioSelecaoLocalidade.setItemCaptionPropertyId("criterio");
            cmbCriterioSelecaoLocalidade.setImmediate(true);
        }
        return cmbCriterioSelecaoLocalidade;
    }

    private Table getTbServicoAssociado() {
        if (tbServicoAssociado == null) {
            tbServicoAssociado = new Table();
            tbServicoAssociado.setWidth(100, Unit.PERCENTAGE);
            tbServicoAssociado.setHeight(300, Unit.PIXELS);
            tbServicoAssociado.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES);
            tbServicoAssociado.addStyleName(ValoTheme.TABLE_COMPACT);
            tbServicoAssociado.setSelectable(true);

            tbServicoAssociado.setColumnCollapsingAllowed(true);
            tbServicoAssociado.setColumnReorderingAllowed(true);
            tbServicoAssociado.setContainerDataSource(getBcServicoAssociado());
            tbServicoAssociado.setSortAscending(false);

            tbServicoAssociado.addGeneratedColumn("servicoAssociado", (Table source, Object itemId, Object columnId) -> {
                Property<Servico> prop = source.getItem(itemId).getItemProperty(columnId);
                if (prop.getType().equals(Servico.class)) {
                    return prop.getValue().getServico();
                }
                return "";
            });
            tbServicoAssociado.addGeneratedColumn("criterioSelecaoLocalidade", (Table source, Object itemId, Object columnId) -> {
                Property<CriterioSelecaoLocalidadeEnum> prop = source.getItem(itemId).getItemProperty(columnId);
                if (prop.getType().equals(CriterioSelecaoLocalidadeEnum.class)) {
                    return prop.getValue().getCriterio();
                }
                return "";
            });

            tbServicoAssociado.setVisibleColumns("servicoAssociado", "criterioSelecaoLocalidade");
            tbServicoAssociado.setColumnExpandRatio("servico", 1);
            tbServicoAssociado.setColumnHeaders("Serviço", "Critério de Localidade");

            tbServicoAssociado.setImmediate(true);
        }

        return tbServicoAssociado;
    }

    private BeanContainer<Integer, Rubrica> getBcRubrica() {
        if (bcRubrica == null) {
            bcRubrica = new BeanContainer<Integer, Rubrica>(Rubrica.class);
            bcRubrica.setBeanIdResolver((Rubrica bean) -> bean.getId());
        }
        return bcRubrica;
    }

    private ComboBox getCmbRubrica() {
        if (cmbRubrica == null) {
            cmbRubrica = new ComboBox("Rubrica");
            cmbRubrica.setInputPrompt("Informe a rubrica...");
            cmbRubrica.setContainerDataSource(getBcRubrica());
            cmbRubrica.setWidth(100, Unit.PERCENTAGE);
            cmbRubrica.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
            cmbRubrica.setItemCaptionPropertyId("rubrica");
            cmbRubrica.setImmediate(true);
        }
        return cmbRubrica;
    }

    private TextField getTxtQtdParcela() {
        if (txtQtdParcela == null) {
            txtQtdParcela = new TextField("Qtd. Parcelas");
            txtQtdParcela.setWidth(100, Unit.PERCENTAGE);
            txtQtdParcela.addValidator(new PositiveIntegerValidator("Qtd. parcelas deve ser um número inteiro e positivo."));
            txtQtdParcela.setValue("0");
        }
        return txtQtdParcela;
    }

    private TextField getTxtValor() {
        if (txtValor == null) {
            txtValor = new TextField("Valor($)");
            txtValor.setWidth(100, Unit.PERCENTAGE);
            txtValor.addValidator(new DoubleValidator("Valor deve ser um número decimal com duas casas."));
            txtValor.setValue("0,00");
        }
        return txtValor;
    }

    private BeanContainer<Long, ServicoCusto> getBcServicoCusto() {
        if (bcServicoCusto == null) {
            bcServicoCusto = new BeanContainer<Long, ServicoCusto>(ServicoCusto.class);
            bcServicoCusto.setBeanIdResolver((ServicoCusto bean) -> bean.getId());
        }
        return bcServicoCusto;
    }

    private Table getTbServicoCusto() {
        if (tbServicoCusto == null) {
            tbServicoCusto = new Table();
            tbServicoCusto.setWidth(100, Unit.PERCENTAGE);
            tbServicoCusto.setHeight(300, Unit.PIXELS);
            tbServicoCusto.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES);
            tbServicoCusto.addStyleName(ValoTheme.TABLE_COMPACT);
            tbServicoCusto.setSelectable(true);

            tbServicoCusto.setColumnCollapsingAllowed(true);
            tbServicoCusto.setColumnReorderingAllowed(true);
            tbServicoCusto.setContainerDataSource(getBcServicoCusto());
            tbServicoCusto.setSortAscending(false);

            tbServicoCusto.addGeneratedColumn("rubrica", (Table source, Object itemId, Object columnId) -> {
                Property<Rubrica> prop = source.getItem(itemId).getItemProperty(columnId);
                if (prop.getType().equals(Rubrica.class)) {
                    return prop.getValue().getRubrica();
                }
                return "";
            });
            tbServicoCusto.addGeneratedColumn("tipoCusto", (Table source, Object itemId, Object columnId) -> {
                Property<TipoCustomEnum> prop = source.getItem(itemId).getItemProperty(columnId);
                if (prop.getType().equals(TipoCustomEnum.class)) {
                    return prop.getValue().getTipoCusto();
                }
                return "";
            });

            tbServicoCusto.setVisibleColumns("rubrica", "tipoCusto", "valor");
            tbServicoCusto.setColumnExpandRatio("rubrica", 1);
            tbServicoCusto.setColumnHeaders("Rubrcica", "Tipo de custo", "Valor");

            tbServicoCusto.setImmediate(true);
        }

        return tbServicoCusto;
    }

    private Button getBtAddRubrica() {
        if (btAddRubrica == null) {
            btAddRubrica = new Button("Adicionar");
            btAddRubrica.setIcon(FontAwesome.PLUS);
            btAddRubrica.addClickListener((Button.ClickEvent event) -> {
                if (getCmbRubrica().getValue() == null) {
                    QGSUI.showWarn("Informe a rubrica.");
                    return;
                }
                if (getTxtValor().getValue() == null || getTxtValor().getValue().isEmpty()) {
                    QGSUI.showWarn("Informe o valor da rubrica.");
                    return;
                }

                if (getOgTipoCusto().getValue().equals(TipoCustomEnum.PARCELADO)) {
                    if (getTxtQtdParcela().getValue() == null || getTxtQtdParcela().getValue().isEmpty()) {
                        QGSUI.showWarn("Informe a qtd. de parcelas da rubrica.");
                        return;
                    }
                    try {
                        getTxtQtdParcela().validate();
                    } catch (Validator.InvalidValueException ex) {
                        QGSUI.doCatch(ex);
                        return;
                    }
                }

                try {
                    getTxtValor().validate();
                } catch (Validator.InvalidValueException ex) {
                    QGSUI.doCatch(ex);
                    return;
                }

                try {
                    ServicoCusto sc = new ServicoCusto();
                    sc.setRubrica(getBcRubrica().getItem(getCmbRubrica().getValue()).getBean());
                    sc.setQtdParcela(Validation.validInteger(getTxtQtdParcela().getValue()));
                    sc.setValor(Validation.validDouble(getTxtValor().getValue()));
                    sc.setTipoCusto(getBcTipoCusto().getItem(getOgTipoCusto().getValue()).getBean());
                    sc.setId(indexServicoCusto--);
                    getBcServicoCusto().addBean(sc);
                    getCmbRubrica().setValue(null);
                    getTxtQtdParcela().setValue("0");
                    getTxtValor().setValue("0");
                    getOgTipoCusto().setValue(TipoCustomEnum.A_VISTA);
                } catch (Exception ex) {
                    QGSUI.doCatch(ex);
                }

            });
        }
        return btAddRubrica;
    }

    private Button getBtDelRubrica() {
        if (btDelRubrica == null) {
            btDelRubrica = new Button("Remover");
            btDelRubrica.setIcon(FontAwesome.MINUS);
            btDelRubrica.addClickListener((Button.ClickEvent event) -> {
                if (getTbServicoCusto().getValue() != null) {
                    ServicoCusto o = getBcServicoCusto().getItem(getTbServicoCusto().getValue()).getBean();
                    getBcServicoCusto().removeItem(o.getId());
                } else {
                    QGSUI.showWarn("Selecione o que deseja remover.");
                }

            });
        }
        return btDelRubrica;
    }

    private BeanContainer<String, TipoCustomEnum> getBcTipoCusto() {
        if (bcTipoCusto == null) {
            bcTipoCusto = new BeanContainer<String, TipoCustomEnum>(TipoCustomEnum.class);
            bcTipoCusto.setBeanIdResolver((TipoCustomEnum bean) -> bean.getTipoCusto());
        }
        return bcTipoCusto;
    }

    private OptionGroup getOgTipoCusto() {
        if (ogTipoCusto == null) {
            ogTipoCusto = new OptionGroup("Forma pagamento", getBcTipoCusto());
            ogTipoCusto.addValueChangeListener(new Property.ValueChangeListener() {
                @Override
                public void valueChange(Property.ValueChangeEvent event) {
                    getTxtQtdParcela().setEnabled(getBcTipoCusto().getItem(getOgTipoCusto().getValue()).getBean().equals(TipoCustomEnum.PARCELADO));
                    getTxtQtdParcela().setValue("0");
                }
            });
            ogTipoCusto.setValue(TipoCustomEnum.A_VISTA);
        }
        return ogTipoCusto;
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
        getDado().setAssociados(new ArrayList<ServicoAssociado>());
        for (Long id : getBcServicoAssociado().getItemIds()) {
            ServicoAssociado sa = getBcServicoAssociado().getItem(id).getBean();
            if (sa.getId() != null && sa.getId() < 0) {
                sa.setId(null);
                sa.setServico(getDado());
            }
            getDado().getAssociados().add(sa);
        }
        getDado().setMateriais(new ArrayList<ServicoMaterial>());
        for (Long id : getBcServicoMaterial().getItemIds()) {
            ServicoMaterial sm = getBcServicoMaterial().getItem(id).getBean();
            if (sm.getId() != null && sm.getId() < 0) {
                sm.setId(null);
                sm.setServico(getDado());
            }
            getDado().getMateriais().add(sm);
        }
        getDado().setCustos(new ArrayList<ServicoCusto>());
        for (Long id : getBcServicoCusto().getItemIds()) {
            ServicoCusto sc = getBcServicoCusto().getItem(id).getBean();
            if (sc.getId() != null && sc.getId() < 0) {
                sc.setId(null);
                sc.setServico(getDado());
            }
            getDado().getCustos().add(sc);
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
        getBcMaterial().addAll(listAllService.findAllByParam(Material.class, "idEmpresa", securityUtils.getEmpresa().getId()));
        getBcServico().addAll(listAllService.findAllByParam(Servico.class, "idEmpresa", securityUtils.getEmpresa().getId()));
        getBcCriterioSelecaoLocalidade().addAll(Arrays.asList(CriterioSelecaoLocalidadeEnum.values()));
        getBcRubrica().addAll(listAllService.findAllByParam(Rubrica.class, "idEmpresa", securityUtils.getEmpresa().getId()));
        getBcTipoCusto().addAll(Arrays.asList(TipoCustomEnum.values()));
    }

    @Override
    protected Class<ServicoView> getSendRefreshClazz() {
        return ServicoView.class;
    }

}
