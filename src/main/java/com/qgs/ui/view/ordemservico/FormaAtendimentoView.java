package com.qgs.ui.view.ordemservico;

import com.qgs.model.Empresa;
import com.qgs.model.ordemservico.FormaAtendimento;
import com.qgs.service.ordemservico.FormaAtendimentoService;
import com.qgs.ui.view.base.BaseView;
import com.qgs.ui.window.base.BaseWindow;
import com.qgs.ui.window.ordemservico.FormaAtendimentoWindow;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import javax.ejb.EJB;
import javax.inject.Inject;

@CDIView(value = FormaAtendimentoView.VIEW_ID)
public final class FormaAtendimentoView extends BaseView<Integer, FormaAtendimento> {

    public static final String VIEW_ID = "formaatendimento";

    private CheckBox ckInativos;

    @EJB
    private FormaAtendimentoService faService;
    @Inject
    private FormaAtendimentoWindow faWindow;

    public FormaAtendimentoView() {
        super("formaatendimento", FontAwesome.TAGS, "Cadastro de Forma Atendimento");

    }

    private CheckBox getCkInativos() {
        if (ckInativos == null) {
            ckInativos = new CheckBox("Inativos?");
            ckInativos.addValueChangeListener((Property.ValueChangeEvent event) -> {
                FormaAtendimentoView.this.doLoadDados();
            });
        }
        return ckInativos;
    }

    @Override
    protected Class<FormaAtendimento> getClazz() {
        return FormaAtendimento.class;
    }

    @Override
    protected String getColumnExpand() {
        return "formaAtendimento";
    }

    @Override
    protected String[] getColumns() {
        return new String[]{"id", "formaAtendimento"};
    }

    @Override
    protected String[] getColumnsLabels() {
        return new String[]{"Código", "Forma Atendimento"};
    }

    @Override
    protected Component[] getExtraToolbarComponents() {
        return new Component[]{getCkInativos()};
    }

    @Override
    protected void doLoadDados(Empresa e) {

        getContainer().removeAllItems();

        FormaAtendimento oTemp = new FormaAtendimento();
        oTemp.setFormaAtendimento(getTxtFilter().getValue());
        oTemp.setAtivo(!getCkInativos().getValue());

        if (e != null) {
            oTemp.setEmpresa(e);
        }

        getContainer().addAll(faService.findAllByFormaAtendimento(oTemp));
    }

    @Override
    protected BaseWindow<Integer, FormaAtendimento> getBaseWindow() {
        return faWindow;
    }

}
