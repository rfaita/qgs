package com.qgs.ui.view.cadastro;

import com.qgs.model.Empresa;
import com.qgs.model.cadastro.EPI;
import com.qgs.service.cadastro.EPIService;
import com.qgs.ui.view.base.BaseView;
import com.qgs.ui.window.base.BaseWindow;
import com.qgs.ui.window.cadastro.EPIWindow;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import javax.ejb.EJB;
import javax.inject.Inject;

@CDIView(value = EPIView.VIEW_ID)
public final class EPIView extends BaseView<Integer, EPI> {

    public static final String VIEW_ID = "epi";

    private CheckBox ckInativos;

    @EJB
    private EPIService epiService;
    @Inject
    private EPIWindow epiWindow;

    public EPIView() {
        super("epi", FontAwesome.SHIELD, "Cadastro de EPI");
    }

    private CheckBox getCkInativos() {
        if (ckInativos == null) {
            ckInativos = new CheckBox("Inativos?");
            ckInativos.addValueChangeListener((Property.ValueChangeEvent event) -> {
                EPIView.this.doLoadDados();
            });
        }
        return ckInativos;
    }

    @Override
    protected Class<EPI> getClazz() {
        return EPI.class;
    }

    @Override
    protected String getColumnExpand() {
        return "epi";
    }

    @Override
    protected String[] getColumns() {
        return new String[]{"id", "epi"};
    }

    @Override
    protected String[] getColumnsLabels() {
        return new String[]{"Código", "EPI"};
    }

    @Override
    protected Component[] getExtraToolbarComponents() {
        return new Component[]{getCkInativos()};
    }

    @Override
    protected void doLoadDados(Empresa e) {
        getContainer().removeAllItems();

        EPI oTemp = new EPI();
        oTemp.setEpi(getTxtFilter().getValue());
        oTemp.setAtivo(!getCkInativos().getValue());

        if (e != null) {
            oTemp.setEmpresa(e);
        }

        getContainer().addAll(epiService.findAllByEPI(oTemp));
    }

    @Override
    protected BaseWindow<Integer, EPI> getBaseWindow() {
        return epiWindow;
    }

}
