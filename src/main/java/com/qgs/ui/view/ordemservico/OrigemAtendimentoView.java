package com.qgs.ui.view.ordemservico;

import com.qgs.model.Empresa;
import com.qgs.model.ordemservico.OrigemAtendimento;
import com.qgs.service.ordemservico.OrigemAtendimentoService;
import com.qgs.ui.view.base.BaseView;
import com.qgs.ui.window.base.BaseWindow;
import com.qgs.ui.window.ordemservico.OrigemAtendimentoWindow;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import javax.ejb.EJB;
import javax.inject.Inject;

@CDIView(value = OrigemAtendimentoView.VIEW_ID)
public final class OrigemAtendimentoView extends BaseView<Integer, OrigemAtendimento> {

    public static final String VIEW_ID = "origematendimento";

    private CheckBox ckInativos;

    @EJB
    private OrigemAtendimentoService oaService;
    @Inject
    private OrigemAtendimentoWindow oaWindow;

    public OrigemAtendimentoView() {
        super("origematendimento", FontAwesome.MAIL_FORWARD, "Cadastro de Origem Atendimento");

    }

    private CheckBox getCkInativos() {
        if (ckInativos == null) {
            ckInativos = new CheckBox("Inativos?");
            ckInativos.addValueChangeListener((Property.ValueChangeEvent event) -> {
                OrigemAtendimentoView.this.doLoadDados();
            });
        }
        return ckInativos;
    }

    @Override
    protected Class<OrigemAtendimento> getClazz() {
        return OrigemAtendimento.class;
    }

    @Override
    protected String getColumnExpand() {
        return "origemAtendimento";
    }

    @Override
    protected String[] getColumns() {
        return new String[]{"id", "origemAtendimento"};
    }

    @Override
    protected String[] getColumnsLabels() {
        return new String[]{"Código", "Origem Atendimento"};
    }

    @Override
    protected Component[] getExtraToolbarComponents() {
        return new Component[]{getCkInativos()};
    }

    @Override
    protected void doLoadDados(Empresa e) {

        getContainer().removeAllItems();

        OrigemAtendimento oTemp = new OrigemAtendimento();
        oTemp.setOrigemAtendimento(getTxtFilter().getValue());
        oTemp.setAtivo(!getCkInativos().getValue());

        if (e != null) {
            oTemp.setEmpresa(e);
        }

        getContainer().addAll(oaService.findAllByOrigemAtendimento(oTemp));
    }

    @Override
    protected BaseWindow<Integer, OrigemAtendimento> getBaseWindow() {
        return oaWindow;
    }

}
