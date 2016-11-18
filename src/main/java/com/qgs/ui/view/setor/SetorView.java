package com.qgs.ui.view.setor;

import com.qgs.model.Empresa;
import com.qgs.model.cadastro.setor.Setor;
import com.qgs.service.setor.SetorService;
import com.qgs.ui.view.base.BaseView;
import com.qgs.ui.window.base.BaseWindow;
import com.qgs.ui.window.setor.SetorWindow;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import javax.ejb.EJB;
import javax.inject.Inject;

@CDIView(value = SetorView.VIEW_ID)
public final class SetorView extends BaseView<Integer, Setor> {

    public static final String VIEW_ID = "setor";

    private CheckBox ckInativos;

    @EJB
    private SetorService setorService;
    @Inject
    private SetorWindow setorWindow;

    public SetorView() {
        super("material", FontAwesome.MAP, "Cadastro de Setores de Serviço");
    }

    private CheckBox getCkInativos() {
        if (ckInativos == null) {
            ckInativos = new CheckBox("Inativos?");
            ckInativos.addValueChangeListener((Property.ValueChangeEvent event) -> {
                SetorView.this.doLoadDados();
            });
        }
        return ckInativos;
    }

    @Override
    protected Class<Setor> getClazz() {
        return Setor.class;
    }

    @Override
    protected String getColumnExpand() {
        return "setor";
    }

    @Override
    protected String[] getColumns() {
        return new String[]{"id", "setor"};
    }

    @Override
    protected String[] getColumnsLabels() {
        return new String[]{"Código", "Setor"};
    }

    @Override
    protected Component[] getExtraToolbarComponents() {
        return new Component[]{getCkInativos()};
    }

    @Override
    protected void doLoadDados(Empresa e) {
        getContainer().removeAllItems();

        Setor oTemp = new Setor();
        oTemp.setSetor(getTxtFilter().getValue());
        oTemp.setAtivo(!getCkInativos().getValue());

        if (e != null) {
            oTemp.setEmpresa(e);
        }

        getContainer().addAll(setorService.findAllBySetor(oTemp));
    }

    @Override
    protected BaseWindow<Integer, Setor> getBaseWindow() {
        return setorWindow;
    }

}
