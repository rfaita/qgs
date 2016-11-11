package com.qgs.ui.window.cadastro;

import com.qgs.model.cadastro.EPI;
import com.qgs.service.cadastro.EPIService;
import com.qgs.ui.QGSUI;
import com.qgs.ui.view.cadastro.EPIView;
import com.qgs.ui.window.base.BaseWindow;
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
public class EPIWindow extends BaseWindow<Integer, EPI> {

    private TextField txtEPI;
    private TextArea txtDescricao;
    private CheckBox ckAtivo;

    @EJB
    private EPIService epiService;

    public EPIWindow() {
        super("epiwindow", "Gestão de EPI");

        FormLayout content = new FormLayout();
        content.setSizeFull();
        content.setMargin(new MarginInfo(true));
        content.setSpacing(true);

        content.addComponent(getTxtId());
        content.addComponent(getTxtEPI());
        content.addComponent(getTxtDescricao());
        content.addComponent(getCkAtivo());
        content.addComponent(getBtSave());
        content.setComponentAlignment(getBtSave(), Alignment.BOTTOM_RIGHT);

        setContent(content);

        setWidth(500, Unit.PIXELS);
        setHeight(90.0f, Unit.PERCENTAGE);

    }

    @Override
    protected void doLoadDados(Integer id) {
        if (id != null && id > 0) {
            setDado(epiService.findById(id));

            getTxtId().setValue(getDado().getId() + "");
            getTxtDescricao().setValue(getDado().getDescricao());
            getTxtEPI().setValue(getDado().getEpi());
            getCkAtivo().setValue(getDado().getAtivo());
        }
    }

    private TextField getTxtEPI() {
        if (txtEPI == null) {
            txtEPI = new TextField("EPI");
            txtEPI.setWidth(100, Unit.PERCENTAGE);
            txtEPI.setRequired(true);
        }
        return txtEPI;
    }

    private TextArea getTxtDescricao() {
        if (txtDescricao == null) {
            txtDescricao = new TextArea("Descrição");
            txtDescricao.setWidth(100, Unit.PERCENTAGE);
            txtDescricao.setNullRepresentation("");
        }
        return txtDescricao;
    }

    private CheckBox getCkAtivo() {
        if (ckAtivo == null) {
            ckAtivo = new CheckBox("Ativo?");
        }
        return ckAtivo;
    }

    @Override
    protected void doSave() {
        if (getDado() == null) {
            setDado(new EPI());
        }
        if (getTxtId().getValue() != null && !getTxtId().getValue().isEmpty()) {
            getDado().setId(Integer.parseInt(getTxtId().getValue()));
        }
        getDado().setEpi(getTxtEPI().getValue());
        getDado().setDescricao(getTxtDescricao().getValue());
        getDado().setAtivo(getCkAtivo().getValue());

        try {
            epiService.save(getDado());
            QGSUI.showInfo("Dados salvos com sucesso.");
            sendRefresh();
            close();

        } catch (Exception ex) {
            QGSUI.doCatch(ex);
        }
    }

    @Override
    protected void doInitiate() {
    }

    @Override
    protected Class<EPIView> getSendRefreshClazz() {
        return EPIView.class;
    }

}
