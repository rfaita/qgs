package com.qgs.ui.window.ordemservico;

import com.qgs.model.ordemservico.OrigemAtendimento;
import com.qgs.service.ordemservico.OrigemAtendimentoService;
import com.qgs.ui.QGSUI;
import com.qgs.ui.view.ordemservico.OrigemAtendimentoView;
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
public class OrigemAtendimentoWindow extends BaseWindow<Integer, OrigemAtendimento> {

    private TextField txtOrigemAtendimento;
    private TextArea txtDescricao;
    private CheckBox ckAtivo;

    @EJB
    private OrigemAtendimentoService oaService;

    public OrigemAtendimentoWindow() {
        super("origematendimentowindow", "Gestão de Origem Atendimento");

        FormLayout content = new FormLayout();
        content.setSizeFull();
        content.setSpacing(true);

        content.addComponent(getTxtId());
        content.addComponent(getTxtOrigemAtendimento());
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
            setDado(oaService.findById(id));

            getTxtId().setValue(getDado().getId() + "");
            getTxtDescricao().setValue(getDado().getDescricao());
            getTxtOrigemAtendimento().setValue(getDado().getOrigemAtendimento());
            getCkAtivo().setValue(getDado().getAtivo());
        }
    }

    private TextField getTxtOrigemAtendimento() {
        if (txtOrigemAtendimento == null) {
            txtOrigemAtendimento = new TextField("Origem Atendimento");
            txtOrigemAtendimento.setWidth(100, Unit.PERCENTAGE);
            txtOrigemAtendimento.setRequired(true);
        }
        return txtOrigemAtendimento;
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
            setDado(new OrigemAtendimento());
        }
        if (getTxtId().getValue() != null && !getTxtId().getValue().isEmpty()) {
            getDado().setId(Integer.parseInt(getTxtId().getValue()));
        }
        getDado().setOrigemAtendimento(getTxtOrigemAtendimento().getValue());
        getDado().setDescricao(getTxtDescricao().getValue());
        getDado().setAtivo(getCkAtivo().getValue());

        try {
            oaService.save(getDado());
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
    protected Class<OrigemAtendimentoView> getSendRefreshClazz() {
        return OrigemAtendimentoView.class;
    }

}
