package com.qgs.ui.view.cadastro.depto;

import com.qgs.model.Empresa;
import com.qgs.model.cadastro.depto.Departamento;
import com.qgs.model.cadastro.depto.TipoDepartamento;
import com.qgs.service.ListAllService;
import com.qgs.service.cadastro.depto.DepartamentoService;
import com.qgs.service.secutiry.SecurityUtils;
import com.qgs.ui.view.base.BaseView;
import com.qgs.ui.window.base.BaseWindow;
import com.qgs.ui.window.cadastro.depto.DepartamentoWindow;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.ui.*;
import javax.ejb.EJB;
import javax.inject.Inject;

@CDIView(value = DepartamentoView.VIEW_ID)
public final class DepartamentoView extends BaseView<Integer, Departamento> {

    public static final String VIEW_ID = "depto";

    private BeanContainer<Integer, TipoDepartamento> bcTipoDepartamento;
    private ComboBox cmbTipoDepartamento;
    private CheckBox ckInativos;

    @EJB
    private DepartamentoService deptoService;
    @EJB
    private ListAllService listAllService;
    @EJB
    private SecurityUtils securityUtils;
    @Inject
    private DepartamentoWindow deptoWindow;

    public DepartamentoView() {
        super("depto", "Cadastro de Departamento");

        getTable().addGeneratedColumn("tipoDepartamento", (Table source, Object itemId, Object columnId) -> {
            Property<TipoDepartamento> prop = source.getItem(itemId).getItemProperty(columnId);
            if (prop.getType().equals(TipoDepartamento.class)) {
                return prop.getValue().getTipoDepartamento();
            }
            return "";
        });
    }

    private CheckBox getCkInativos() {
        if (ckInativos == null) {
            ckInativos = new CheckBox("Inativos?");
            ckInativos.addValueChangeListener((Property.ValueChangeEvent event) -> {
                DepartamentoView.this.doLoadDados();
            });
        }
        return ckInativos;
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
            cmbTipoDepartamento = new ComboBox();
            cmbTipoDepartamento.setInputPrompt("Tipo departamento");
            cmbTipoDepartamento.setContainerDataSource(getBcTipoDepartamento());
            cmbTipoDepartamento.setWidth(100, Unit.PERCENTAGE);
            cmbTipoDepartamento.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
            cmbTipoDepartamento.setItemCaptionPropertyId("tipoDepartamento");
            cmbTipoDepartamento.setImmediate(true);
            cmbTipoDepartamento.addValueChangeListener((Property.ValueChangeEvent event) -> {
                doLoadDados();
            });
        }
        return cmbTipoDepartamento;
    }

    @Override
    protected Class<Departamento> getClazz() {
        return Departamento.class;
    }

    @Override
    protected String getColumnExpand() {
        return "departamento";
    }

    @Override
    protected String[] getColumns() {
        return new String[]{"id", "departamento", "tipoDepartamento"};
    }

    @Override
    protected String[] getColumnsLabels() {
        return new String[]{"Código", "Departamento", "Tipo Departamento"};
    }

    @Override
    protected Component[] getExtraToolbarComponents() {
        return new Component[]{getCmbTipoDepartamento(), getCkInativos()};
    }

    @Override
    protected void doLoadDados(Empresa e) {

        if (getBcTipoDepartamento().size() <= 0) {
            getBcTipoDepartamento().addAll(listAllService.findAllByParam(TipoDepartamento.class, "idEmpresa", securityUtils.getEmpresa().getId()));
        }

        getContainer().removeAllItems();

        Departamento oTemp = new Departamento();
        oTemp.setDepartamento(getTxtFilter().getValue());
        oTemp.setAtivo(!getCkInativos().getValue());
        if (getCmbTipoDepartamento().getValue() != null) {
            oTemp.setTipoDepartamento(getBcTipoDepartamento().getItem(getCmbTipoDepartamento().getValue()).getBean());
        } else {
            oTemp.setTipoDepartamento(null);
        }

        if (e != null) {
            oTemp.setEmpresa(e);
        }

        getContainer().addAll(deptoService.findAllByDepartamento(oTemp));
    }

    @Override
    protected BaseWindow<Integer, Departamento> getBaseWindow() {
        return deptoWindow;
    }

}
