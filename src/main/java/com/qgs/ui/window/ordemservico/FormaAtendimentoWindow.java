package com.qgs.ui.window.ordemservico;

import com.qgs.model.ordemservico.FormaAtendimento;
import com.qgs.service.ordemservico.FormaAtendimentoService;
import com.qgs.ui.QGSUI;
import com.qgs.ui.view.ordemservico.FormaAtendimentoView;
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
public class FormaAtendimentoWindow extends BaseWindow<Integer, FormaAtendimento> {

    private TextField txtFormaAtendimento;
    private TextArea txtDescricao;
    private CheckBox ckAtivo;

    @EJB
    private FormaAtendimentoService epiService;

    public FormaAtendimentoWindow() {
        super("formaatendimentowindow", "Gestão de Forma Atendimento");

        FormLayout content = new FormLayout();
        content.setSizeFull();
        content.setSpacing(true);

        content.addComponent(getTxtId());
        content.addComponent(getTxtFormaAtendimento());
        content.addComponent(getTxtDescricao());
        content.addComponent(getCkAtivo());

        VerticalLayout vl = new VerticalLayout(content, getBtSave());

        vl.setComponentAlignment(getBtSave(), Alignment.BOTTOM_RIGHT);
        vl.setMargin(new MarginInfo(true));

        setContent(vl);
    }

    @Override
    protected void doLoadDados(Integer id) {
        if (id != null && id > 0) {
            setDado(epiService.findById(id));

            getTxtId().setValue(getDado().getId() + "");
            getTxtDescricao().setValue(getDado().getDescricao());
            getTxtFormaAtendimento().setValue(getDado().getFormaAtendimento());
            getCkAtivo().setValue(getDado().getAtivo());
        }
    }

    private TextField getTxtFormaAtendimento() {
        if (txtFormaAtendimento == null) {
            txtFormaAtendimento = new TextField("Forma Atendimento");
            txtFormaAtendimento.setWidth(100, Unit.PERCENTAGE);
            txtFormaAtendimento.setRequired(true);
        }
        return txtFormaAtendimento;
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
            setDado(new FormaAtendimento());
        }
        if (getTxtId().getValue() != null && !getTxtId().getValue().isEmpty()) {
            getDado().setId(Integer.parseInt(getTxtId().getValue()));
        }
        getDado().setFormaAtendimento(getTxtFormaAtendimento().getValue());
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
    protected Class<FormaAtendimentoView> getSendRefreshClazz() {
        return FormaAtendimentoView.class;
    }

}
