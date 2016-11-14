package com.qgs.ui.view.cadastro;

import com.qgs.model.Empresa;
import com.qgs.model.cadastro.Atributo;
import com.qgs.model.cadastro.TipoAtributo;
import com.qgs.service.cadastro.AtributoService;
import com.qgs.ui.view.base.BaseView;
import com.qgs.ui.window.base.BaseWindow;
import com.qgs.ui.window.cadastro.AtributoWindow;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property;
import com.vaadin.ui.*;
import javax.ejb.EJB;
import javax.inject.Inject;

@CDIView(value = AtributoView.VIEW_ID)
public final class AtributoView extends BaseView<Integer, Atributo> {

    public static final String VIEW_ID = "atributo";

    private CheckBox ckInativos;

    @EJB
    private AtributoService atrService;
    @Inject
    private AtributoWindow atrWindow;

    public AtributoView() {
        super("atributo", "Cadastro de Atributo");

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

    @Override
    protected Class<Atributo> getClazz() {
        return Atributo.class;
    }

    @Override
    protected String getColumnExpand() {
        return "material";
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
        return new Component[]{getCkInativos()};
    }

    @Override
    protected void doLoadDados(Empresa e) {
        getContainer().removeAllItems();

        Atributo oTemp = new Atributo();
        oTemp.setAtributo(getTxtFilter().getValue());
        oTemp.setAtivo(!getCkInativos().getValue());

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