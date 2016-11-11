package com.qgs.ui.view.cadastro;

import com.qgs.model.Empresa;
import com.qgs.model.cadastro.Material;
import com.qgs.model.cadastro.TipoMaterial;
import com.qgs.service.cadastro.MaterialService;
import com.qgs.ui.view.base.BaseView;
import com.qgs.ui.window.base.BaseWindow;
import com.qgs.ui.window.cadastro.MaterialWindow;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property;
import com.vaadin.ui.*;
import javax.ejb.EJB;
import javax.inject.Inject;

@CDIView(value = MaterialView.VIEW_ID)
public final class MaterialView extends BaseView<Integer, Material> {

    public static final String VIEW_ID = "material";

    private CheckBox ckInativos;

    @EJB
    private MaterialService matService;
    @Inject
    private MaterialWindow matWindow;

    public MaterialView() {
        super("material", "Cadastro de Material");

        getTable().addGeneratedColumn("tipoMaterial", (Table source, Object itemId, Object columnId) -> {
            Property<TipoMaterial> prop = source.getItem(itemId).getItemProperty(columnId);
            if (prop.getType().equals(TipoMaterial.class)) {
                return prop.getValue().getTipoMaterial();
            }
            return "";
        });

    }

    private CheckBox getCkInativos() {
        if (ckInativos == null) {
            ckInativos = new CheckBox("Inativos?");
            ckInativos.addValueChangeListener((Property.ValueChangeEvent event) -> {
                MaterialView.this.doLoadDados();
            });
        }
        return ckInativos;
    }

    @Override
    protected Class<Material> getClazz() {
        return Material.class;
    }

    @Override
    protected String getColumnExpand() {
        return "material";
    }

    @Override
    protected String[] getColumns() {
        return new String[]{"id", "material", "tipoMaterial"};
    }

    @Override
    protected String[] getColumnsLabels() {
        return new String[]{"Código", "Material", "Tipo Material"};
    }

    @Override
    protected Component[] getExtraToolbarComponents() {
        return new Component[]{getCkInativos()};
    }

    @Override
    protected void doLoadDados(Empresa e) {
        getContainer().removeAllItems();

        Material oTemp = new Material();
        oTemp.setMaterial(getTxtFilter().getValue());
        oTemp.setAtivo(!getCkInativos().getValue());

        if (e != null) {
            oTemp.setEmpresa(e);
        }

        getContainer().addAll(matService.findAllByMaterial(oTemp));
    }

    @Override
    protected BaseWindow<Integer, Material> getBaseWindow() {
        return matWindow;
    }

}
