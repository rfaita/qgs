package com.qgs.ui.view.cadastro;

import com.qgs.model.Empresa;
import com.qgs.model.cadastro.Atributo;
import com.qgs.model.cadastro.TipoAtributo;
import com.qgs.service.ListAllService;
import com.qgs.service.cadastro.AtributoService;
import com.qgs.ui.view.base.BaseView;
import com.qgs.ui.window.base.BaseWindow;
import com.qgs.ui.window.cadastro.AtributoWindow;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import javax.ejb.EJB;
import javax.inject.Inject;

@CDIView(value = AtributoView.VIEW_ID)
public final class AtributoView extends BaseView<Integer, Atributo> {

    public static final String VIEW_ID = "atributo";

    private CheckBox ckInativos;
    private BeanContainer<Integer, TipoAtributo> bcTipoAtributo;
    private ComboBox cmbTipoAtributo;

    @EJB
    private AtributoService atrService;
    @EJB
    private ListAllService listAllService;
    @Inject
    private AtributoWindow atrWindow;

    public AtributoView() {
        super("atributo", FontAwesome.CUBES, "Cadastro de Atributo");

        getTable().addGeneratedColumn("tipoAtributo", (Table source, Object itemId, Object columnId) -> {
            Property<TipoAtributo> prop = source.getItem(itemId).getItemProperty(columnId);
            if (prop.getType().equals(TipoAtributo.class)) {
                return prop.getValue().getTipoAtributo();
            }
            return "";
        });

    }

    private CheckBox getCkInativos() {
        if (ckInativos == null) {
            ckInativos = new CheckBox("Inativos?");
            ckInativos.addValueChangeListener((Property.ValueChangeEvent event) -> {
                AtributoView.this.doLoadDados();
            });
        }
        return ckInativos;
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
            cmbTipoAtributo = new ComboBox();
            cmbTipoAtributo.setInputPrompt("Tipo atributo");
            cmbTipoAtributo.setContainerDataSource(getBcTipoAtributo());
            cmbTipoAtributo.setWidth(100, Unit.PERCENTAGE);
            cmbTipoAtributo.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
            cmbTipoAtributo.setItemCaptionPropertyId("tipoAtributo");
            cmbTipoAtributo.setImmediate(true);
            cmbTipoAtributo.addValueChangeListener((Property.ValueChangeEvent event) -> {
                doLoadDados();
            });
        }
        return cmbTipoAtributo;
    }

    @Override
    protected Class<Atributo> getClazz() {
        return Atributo.class;
    }

    @Override
    protected String getColumnExpand() {
        return "atributo";
    }

    @Override
    protected String[] getColumns() {
        return new String[]{"id", "atributo", "tipoAtributo"};
    }

    @Override
    protected String[] getColumnsLabels() {
        return new String[]{"Código", "Atributo", "Tipo Atributo"};
    }

    @Override
    protected Component[] getExtraToolbarComponents() {
        return new Component[]{getCmbTipoAtributo(), getCkInativos()};
    }

    @Override
    protected void doLoadDados(Empresa e) {

        if (getBcTipoAtributo().size() <= 0) {
            getBcTipoAtributo().addAll(listAllService.findAll(TipoAtributo.class));
        }

        getContainer().removeAllItems();

        Atributo oTemp = new Atributo();
        oTemp.setAtributo(getTxtFilter().getValue());
        oTemp.setAtivo(!getCkInativos().getValue());
        if (getCmbTipoAtributo().getValue() != null) {
            oTemp.setTipoAtributo(getBcTipoAtributo().getItem(getCmbTipoAtributo().getValue()).getBean());
        } else {
            oTemp.setTipoAtributo(null);
        }

        if (e != null) {
            oTemp.setEmpresa(e);
        }

        getContainer().addAll(atrService.findAllByAtributo(oTemp));
    }

    @Override
    protected BaseWindow<Integer, Atributo> getBaseWindow() {
        return atrWindow;
    }

}
