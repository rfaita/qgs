package com.qgs.ui.window.cadastro;

import com.qgs.enums.TipoDadoAtributoEnum;
import com.qgs.model.cadastro.Atributo;
import com.qgs.model.cadastro.TipoAtributo;
import com.qgs.model.cadastro.ValorAtributo;
import com.qgs.service.ListAllService;
import com.qgs.service.cadastro.AtributoService;
import com.qgs.ui.QGSUI;
import com.qgs.ui.view.cadastro.AtributoView;
import com.qgs.ui.window.base.BaseWindow;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanContainer;
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
public class AtributoWindow extends BaseWindow<Integer, Atributo> {

    private TextField txtAtributo;
    private TextField txtValorAtributo;
    private TextArea txtDescricao;
    private Button btAdd;
    private Button btDel;
    private CheckBox ckAtivo;
    private BeanContainer<Integer, TipoAtributo> bcTipoAtributo;
    private ComboBox cmbTipoAtributo;
    private BeanContainer<Long, ValorAtributo> bcValorAtributo;
    private Table tbValorAtributo;
    private Long index = -1L;

    @EJB
    private AtributoService atrService;
    @EJB
    private ListAllService listAllService;

    public AtributoWindow() {
        super("atributowindow", "Gestão de Atributo");

        FormLayout content = new FormLayout();
        content.setSizeFull();
        content.setSpacing(true);

        content.addComponent(getTxtId());
        content.addComponent(getTxtAtributo());
        content.addComponent(getTxtDescricao());
        content.addComponent(getCkAtivo());
        content.addComponent(getCmbTipoAtributo());
        content.addComponent(getTxtValorAtributo());

        HorizontalLayout h = new HorizontalLayout(getBtAdd(), getBtDel());
        h.setWidth(100, Unit.PERCENTAGE);

        content.addComponent(h);
        content.addComponent(getTbValorAtributo());

        VerticalLayout vl = new VerticalLayout(content, getBtSave());

        vl.setComponentAlignment(getBtSave(), Alignment.BOTTOM_RIGHT);
        vl.setMargin(new MarginInfo(true));

        setContent(vl);
    }

    @Override
    protected void doLoadDados(Integer id) {
        if (id != null && id > 0) {
            setDado(atrService.findCompleteById(id));

            getTxtId().setValue(getDado().getId() + "");
            getTxtDescricao().setValue(getDado().getDescricao());
            if (getDado().getTipoAtributo() != null) {
                getCmbTipoAtributo().setValue(getDado().getTipoAtributo().getId());
            }
            getTxtAtributo().setValue(getDado().getAtributo());
            getCkAtivo().setValue(getDado().getAtivo());
            getBcValorAtributo().addAll(getDado().getValoresAtributo());
        }
    }

    private TextField getTxtAtributo() {
        if (txtAtributo == null) {
            txtAtributo = new TextField("Atributo");
            txtAtributo.setWidth(100, Unit.PERCENTAGE);
            txtAtributo.setRequired(true);
        }
        return txtAtributo;
    }

    private TextField getTxtValorAtributo() {
        if (txtValorAtributo == null) {
            txtValorAtributo = new TextField("Valor Atributo");
            txtValorAtributo.setEnabled(Boolean.FALSE);
            txtValorAtributo.setWidth(100, Unit.PERCENTAGE);
        }
        return txtValorAtributo;
    }

    private TextArea getTxtDescricao() {
        if (txtDescricao == null) {
            txtDescricao = new TextArea("Descrição");
            txtDescricao.setWidth(100, Unit.PERCENTAGE);
            txtDescricao.setNullRepresentation("");
        }
        return txtDescricao;
    }

    private Button getBtAdd() {
        if (btAdd == null) {
            btAdd = new Button("Adicionar Valor");
            btAdd.setIcon(FontAwesome.PLUS);
            btAdd.setEnabled(Boolean.FALSE);
            btAdd.addClickListener((Button.ClickEvent event) -> {
                if (getTxtValorAtributo().getValue() == null || getTxtValorAtributo().getValue().isEmpty()) {
                    QGSUI.showWarn("Informe o valor do atributo.");
                    return;
                }
                ValorAtributo valor = new ValorAtributo();
                valor.setId(index--);
                valor.setValorAtributo(getTxtValorAtributo().getValue());
                getBcValorAtributo().addBean(valor);
                getTxtValorAtributo().setValue("");

            });
        }
        return btAdd;
    }

    private Button getBtDel() {
        if (btDel == null) {
            btDel = new Button("Remover Valor");
            btDel.setIcon(FontAwesome.MINUS);
            btDel.setEnabled(Boolean.FALSE);
            btDel.addClickListener((Button.ClickEvent event) -> {
                if (getTbValorAtributo().getValue() != null) {
                    ValorAtributo o = getBcValorAtributo().getItem(getTbValorAtributo().getValue()).getBean();
                    getBcValorAtributo().removeItem(o.getId());
                } else {
                    QGSUI.showWarn("Selecione o que deseja remover.");
                }

            });
        }
        return btDel;
    }

    private BeanContainer<Integer, TipoAtributo> getBcTipoAtributo() {
        if (bcTipoAtributo == null) {
            bcTipoAtributo = new BeanContainer<Integer, TipoAtributo>(TipoAtributo.class);
            bcTipoAtributo.setBeanIdResolver((TipoAtributo bean) -> bean.getId());
        }
        return bcTipoAtributo;
    }

    private ComboBox getCmbTipoAtributo() {
        if (cmbTipoAtributo == null) {
            cmbTipoAtributo = new ComboBox("Tipo atributo");
            cmbTipoAtributo.setInputPrompt("Informe o tipo...");
            cmbTipoAtributo.setContainerDataSource(getBcTipoAtributo());
            cmbTipoAtributo.setWidth(100, Unit.PERCENTAGE);
            cmbTipoAtributo.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
            cmbTipoAtributo.setItemCaptionPropertyId("tipoAtributo");
            cmbTipoAtributo.setImmediate(true);
            cmbTipoAtributo.setRequired(true);
            cmbTipoAtributo.addValueChangeListener((Property.ValueChangeEvent event) -> {

                TipoAtributo t = getBcTipoAtributo().getItem(getCmbTipoAtributo().getValue()).getBean();

                getTxtValorAtributo().setEnabled(t.getTipoDado() != null && t.getTipoDado().equals(TipoDadoAtributoEnum.NORMALIZADO));
                getBtAdd().setEnabled(t.getTipoDado() != null && t.getTipoDado().equals(TipoDadoAtributoEnum.NORMALIZADO));
                getBtDel().setEnabled(t.getTipoDado() != null && t.getTipoDado().equals(TipoDadoAtributoEnum.NORMALIZADO));

            });
        }
        return cmbTipoAtributo;
    }

    private CheckBox getCkAtivo() {
        if (ckAtivo == null) {
            ckAtivo = new CheckBox("Ativo?");
        }
        return ckAtivo;
    }

    private BeanContainer<Long, ValorAtributo> getBcValorAtributo() {
        if (bcValorAtributo == null) {
            bcValorAtributo = new BeanContainer<Long, ValorAtributo>(ValorAtributo.class);
            bcValorAtributo.setBeanIdResolver((ValorAtributo bean) -> bean.getId());
        }
        return bcValorAtributo;
    }

    private Table getTbValorAtributo() {
        if (tbValorAtributo == null) {
            tbValorAtributo = new Table();
            tbValorAtributo.setWidth(100, Unit.PERCENTAGE);
            tbValorAtributo.setHeight(200, Unit.PIXELS);
            tbValorAtributo.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES);
            tbValorAtributo.addStyleName(ValoTheme.TABLE_COMPACT);
            tbValorAtributo.setSelectable(true);

            tbValorAtributo.setColumnCollapsingAllowed(true);
            tbValorAtributo.setColumnReorderingAllowed(true);
            tbValorAtributo.setContainerDataSource(getBcValorAtributo());
            tbValorAtributo.setSortAscending(false);

            tbValorAtributo.setVisibleColumns("valorAtributo");
            tbValorAtributo.setColumnExpandRatio("valorAtributo", 1);
            tbValorAtributo.setColumnHeaders("Valor do atributo");

            tbValorAtributo.setImmediate(true);
        }

        return tbValorAtributo;
    }

    @Override
    protected void doSave() {
        if (getDado() == null) {
            setDado(new Atributo());
        }
        if (getTxtId().getValue() != null && !getTxtId().getValue().isEmpty()) {
            getDado().setId(Integer.parseInt(getTxtId().getValue()));
        }
        getDado().setAtributo(getTxtAtributo().getValue());
        getDado().setDescricao(getTxtDescricao().getValue());
        getDado().setAtivo(getCkAtivo().getValue());
        if (getCmbTipoAtributo().getValue() != null) {
            getDado().setTipoAtributo(getBcTipoAtributo().getItem(getCmbTipoAtributo().getValue()).getBean());
        } else {
            getDado().setTipoAtributo(null);
        }
        getDado().setValoresAtributo(new ArrayList<ValorAtributo>());
        for (Long id : getBcValorAtributo().getItemIds()) {
            ValorAtributo va = getBcValorAtributo().getItem(id).getBean();
            if (va.getId() != null && va.getId() < 0) {
                va.setId(null);
                va.setAtributo(getDado());
            }
            getDado().getValoresAtributo().add(va);
        }

        try {
            atrService.save(getDado());
            QGSUI.showInfo("Dados salvos com sucesso.");
            sendRefresh();
            close();

        } catch (Exception ex) {
            QGSUI.doCatch(ex);
        }
    }

    @Override
    protected void doInitiate() {
        getBcTipoAtributo().addAll(listAllService.findAll(TipoAtributo.class));
    }

    @Override
    protected Class<AtributoView> getSendRefreshClazz() {
        return AtributoView.class;
    }

}
