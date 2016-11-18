package com.qgs.ui.view.cadastro.rubrica;

import com.qgs.model.Empresa;
import com.qgs.model.cadastro.rubrica.Rubrica;
import com.qgs.model.cadastro.rubrica.TipoRubrica;
import com.qgs.service.ListAllService;
import com.qgs.service.cadastro.rubrica.RubricaService;
import com.qgs.ui.view.base.BaseView;
import com.qgs.ui.window.base.BaseWindow;
import com.qgs.ui.window.cadastro.rubrica.RubricaWindow;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import javax.ejb.EJB;
import javax.inject.Inject;

@CDIView(value = RubricaView.VIEW_ID)
public final class RubricaView extends BaseView<Integer, Rubrica> {

    public static final String VIEW_ID = "rubrica";

    private CheckBox ckInativos;
    private BeanContainer<Integer, TipoRubrica> bcTipoRubrica;
    private ComboBox cmbTipoRubrica;

    @EJB
    private RubricaService rubricaService;
    @EJB
    private ListAllService listAllService;
    @Inject
    private RubricaWindow rubricaWindow;

    public RubricaView() {
        super("rubrica", FontAwesome.DOLLAR, "Cadastro de Rubrica");

        getTable().addGeneratedColumn("tipoRubrica", (Table source, Object itemId, Object columnId) -> {
            Property<TipoRubrica> prop = source.getItem(itemId).getItemProperty(columnId);
            if (prop.getType().equals(TipoRubrica.class)) {
                return prop.getValue().getTipoRubrica();
            }
            return "";
        });

    }

    private CheckBox getCkInativos() {
        if (ckInativos == null) {
            ckInativos = new CheckBox("Inativas?");
            ckInativos.addValueChangeListener((Property.ValueChangeEvent event) -> {
                RubricaView.this.doLoadDados();
            });
        }
        return ckInativos;
    }

    private BeanContainer<Integer, TipoRubrica> getBcTipoRubrica() {
        if (bcTipoRubrica == null) {
            bcTipoRubrica = new BeanContainer<Integer, TipoRubrica>(TipoRubrica.class);
            bcTipoRubrica.setBeanIdResolver((TipoRubrica bean) -> bean.getId());
        }
        return bcTipoRubrica;
    }

    private ComboBox getCmbTipoRubrica() {
        if (cmbTipoRubrica == null) {
            cmbTipoRubrica = new ComboBox();
            cmbTipoRubrica.setInputPrompt("Tipo rubrica");
            cmbTipoRubrica.setContainerDataSource(getBcTipoRubrica());
            cmbTipoRubrica.setWidth(100, Unit.PERCENTAGE);
            cmbTipoRubrica.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
            cmbTipoRubrica.setItemCaptionPropertyId("tipoRubrica");
            cmbTipoRubrica.setImmediate(true);
            cmbTipoRubrica.addValueChangeListener((Property.ValueChangeEvent event) -> {
                doLoadDados();
            });
        }
        return cmbTipoRubrica;
    }

    @Override
    protected Class<Rubrica> getClazz() {
        return Rubrica.class;
    }

    @Override
    protected String getColumnExpand() {
        return "rubrica";
    }

    @Override
    protected String[] getColumns() {
        return new String[]{"id", "rubrica", "tipoRubrica"};
    }

    @Override
    protected String[] getColumnsLabels() {
        return new String[]{"Código", "Rubrica", "Tipo Rubrica"};
    }

    @Override
    protected Component[] getExtraToolbarComponents() {
        return new Component[]{getCmbTipoRubrica(), getCkInativos()};
    }

    @Override
    protected void doLoadDados(Empresa e) {

        if (getBcTipoRubrica().size() <= 0) {
            getBcTipoRubrica().addAll(listAllService.findAll(TipoRubrica.class));
        }

        getContainer().removeAllItems();

        Rubrica oTemp = new Rubrica();
        oTemp.setRubrica(getTxtFilter().getValue());
        oTemp.setAtivo(!getCkInativos().getValue());
        if (getCmbTipoRubrica().getValue() != null) {
            oTemp.setTipoRubrica(getBcTipoRubrica().getItem(getCmbTipoRubrica().getValue()).getBean());
        } else {
            oTemp.setTipoRubrica(null);
        }

        if (e != null) {
            oTemp.setEmpresa(e);
        }

        getContainer().addAll(rubricaService.findAllByRubrica(oTemp));
    }

    @Override
    protected BaseWindow<Integer, Rubrica> getBaseWindow() {
        return rubricaWindow;
    }

}
