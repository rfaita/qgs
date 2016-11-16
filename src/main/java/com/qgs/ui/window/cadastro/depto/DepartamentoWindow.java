package com.qgs.ui.window.cadastro.depto;

import com.qgs.model.cadastro.depto.Departamento;
import com.qgs.model.cadastro.depto.TipoDepartamento;
import com.qgs.service.ListAllService;
import com.qgs.service.cadastro.depto.DepartamentoService;
import com.qgs.service.secutiry.SecurityUtils;
import com.qgs.ui.QGSUI;
import com.qgs.ui.view.cadastro.depto.DepartamentoView;
import com.qgs.ui.window.base.BaseWindow;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author rafael
 */
@RequestScoped
public class DepartamentoWindow extends BaseWindow<Integer, Departamento> {

    private TextField txtDepartamento;
    private BeanContainer<Integer, TipoDepartamento> bcTipoDepartamento;
    private ComboBox cmbTipoDepartamento;
    private CheckBox ckAtivo;

    @EJB
    private DepartamentoService deptoService;
    @EJB
    private ListAllService listAllService;
    @EJB
    private SecurityUtils securityUtils;

    public DepartamentoWindow() {
        super("deptowindow", "Gestão de Departamento");

        FormLayout content = new FormLayout();
        content.setSizeFull();
        content.setSpacing(true);

        content.addComponent(getTxtId());
        content.addComponent(getTxtDepartamento());
        content.addComponent(getCmbTipoDepartamento());
        content.addComponent(getCkAtivo());

        VerticalLayout vl = new VerticalLayout(content, getBtSave());

        vl.setComponentAlignment(getBtSave(), Alignment.BOTTOM_RIGHT);
        vl.setMargin(new MarginInfo(true));

        setContent(vl);
    }

    @Override
    protected void doLoadDados(Integer id) {
        if (id != null && id > 0) {
            setDado(deptoService.findById(id));

            getTxtId().setValue(getDado().getId() + "");
            getTxtDepartamento().setValue(getDado().getDepartamento());
            if (getDado().getTipoDepartamento() != null) {
                getCmbTipoDepartamento().setValue(getDado().getTipoDepartamento().getId());
            }
            getCkAtivo().setValue(getDado().getAtivo());
        }
    }

    private TextField getTxtDepartamento() {
        if (txtDepartamento == null) {
            txtDepartamento = new TextField("Departamento");
            txtDepartamento.setWidth(100, Unit.PERCENTAGE);
            txtDepartamento.setRequired(true);
        }
        return txtDepartamento;
    }

    private CheckBox getCkAtivo() {
        if (ckAtivo == null) {
            ckAtivo = new CheckBox("Ativo?");
        }
        return ckAtivo;
    }

    private BeanContainer<Integer, TipoDepartamento> getBcTipoDepartamento() {
        if (bcTipoDepartamento == null) {
            bcTipoDepartamento = new BeanContainer<Integer, TipoDepartamento>(TipoDepartamento.class);
            bcTipoDepartamento.setBeanIdResolver((TipoDepartamento bean) -> bean.getId());
        }
        return bcTipoDepartamento;
    }

    private ComboBox getCmbTipoDepartamento() {
        if (cmbTipoDepartamento == null) {
            cmbTipoDepartamento = new ComboBox("Tipo departamento");
            cmbTipoDepartamento.setInputPrompt("Informe o tipo...");
            cmbTipoDepartamento.setContainerDataSource(getBcTipoDepartamento());
            cmbTipoDepartamento.setWidth(100, Unit.PERCENTAGE);
            cmbTipoDepartamento.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
            cmbTipoDepartamento.setItemCaptionPropertyId("tipoDepartamento");
            cmbTipoDepartamento.setImmediate(true);
            cmbTipoDepartamento.setRequired(true);
        }
        return cmbTipoDepartamento;
    }

    @Override
    protected void doSave() {
        if (getDado() == null) {
            setDado(new Departamento());
        }
        if (getTxtId().getValue() != null && !getTxtId().getValue().isEmpty()) {
            getDado().setId(Integer.parseInt(getTxtId().getValue()));
        }
        getDado().setDepartamento(getTxtDepartamento().getValue());
        getDado().setAtivo(getCkAtivo().getValue());
        if (getCmbTipoDepartamento().getValue() != null) {
            getDado().setTipoDepartamento(getBcTipoDepartamento().getItem(getCmbTipoDepartamento().getValue()).getBean());
        } else {
            getDado().setTipoDepartamento(null);
        }

        try {
            deptoService.save(getDado());
            QGSUI.showInfo("Dados salvos com sucesso.");
            sendRefresh();
            close();

        } catch (Exception ex) {
            QGSUI.doCatch(ex);
        }
    }

    @Override
    protected void doInitiate() {
        getBcTipoDepartamento().addAll(listAllService.findAllByParam(TipoDepartamento.class, "idEmpresa", securityUtils.getEmpresa().getId()));
    }

    @Override
    protected Class<DepartamentoView> getSendRefreshClazz() {
        return DepartamentoView.class;
    }

}
