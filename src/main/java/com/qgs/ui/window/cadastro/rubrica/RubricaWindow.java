package com.qgs.ui.window.cadastro.rubrica;

import com.qgs.model.cadastro.rubrica.Rubrica;
import com.qgs.model.cadastro.rubrica.TipoRubrica;
import com.qgs.service.ListAllService;
import com.qgs.service.cadastro.rubrica.RubricaService;
import com.qgs.ui.QGSUI;
import com.qgs.ui.view.cadastro.rubrica.RubricaView;
import com.qgs.ui.window.base.BaseWindow;
import com.vaadin.data.util.BeanContainer;
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
public class RubricaWindow extends BaseWindow<Integer, Rubrica> {

    private TextField txtRubrica;
    private TextField txtRubricaResumida;
    private TextArea txtDescricao;
    private CheckBox ckAtivo;
    private BeanContainer<Integer, TipoRubrica> bcTipoRubrica;
    private ComboBox cmbTipoRubrica;

    @EJB
    private RubricaService rubricaService;
    @EJB
    private ListAllService listAllService;

    public RubricaWindow() {
        super("rubricawindow", "Gestão de Rubrica");

        FormLayout content = new FormLayout();
        content.setSizeFull();
        content.setSpacing(true);

        content.addComponent(getTxtId());
        content.addComponent(getTxtRubrica());
        content.addComponent(getTxtRubricaResumida());
        content.addComponent(getTxtDescricao());
        content.addComponent(getCmbTipoRubrica());
        content.addComponent(getCkAtivo());

        VerticalLayout vl = new VerticalLayout(content, getBtSave());

        vl.setComponentAlignment(getBtSave(), Alignment.BOTTOM_RIGHT);
        vl.setMargin(new MarginInfo(true));

        setContent(vl);

    }

    @Override
    protected void doLoadDados(Integer id) {
        if (id != null && id > 0) {
            setDado(rubricaService.findById(id));

            getTxtId().setValue(getDado().getId() + "");
            getTxtDescricao().setValue(getDado().getDescricao());
            if (getDado().getTipoRubrica() != null) {
                getCmbTipoRubrica().setValue(getDado().getTipoRubrica().getId());
            }
            getTxtRubrica().setValue(getDado().getRubrica());
            getTxtRubricaResumida().setValue(getDado().getRubricaResumida());
            getCkAtivo().setValue(getDado().getAtivo());
        }
    }

    private TextField getTxtRubrica() {
        if (txtRubrica == null) {
            txtRubrica = new TextField("Rubrica");
            txtRubrica.setWidth(100, Unit.PERCENTAGE);
            txtRubrica.setRequired(true);
        }
        return txtRubrica;
    }

    private TextField getTxtRubricaResumida() {
        if (txtRubricaResumida == null) {
            txtRubricaResumida = new TextField("Rubrica resumida");
            txtRubricaResumida.setWidth(100, Unit.PERCENTAGE);
            txtRubricaResumida.setRequired(true);
        }
        return txtRubricaResumida;
    }

    private TextArea getTxtDescricao() {
        if (txtDescricao == null) {
            txtDescricao = new TextArea("Descrição");
            txtDescricao.setWidth(100, Unit.PERCENTAGE);
            txtDescricao.setNullRepresentation("");
        }
        return txtDescricao;
    }

    private BeanContainer<Integer, TipoRubrica> getBcTipoRubrica() {
        if (bcTipoRubrica == null) {
            bcTipoRubrica = new BeanContainer<Integer, TipoRubrica>(TipoRubrica.class);
            bcTipoRubrica.setBeanIdResolver((TipoRubrica bean) -> bean.getId());
        }
        return bcTipoRubrica;
    }

    private ComboBox getCmbTipoRubrica() {
        if (cmbTipoRubrica == null) {
            cmbTipoRubrica = new ComboBox("Tipo material");
            cmbTipoRubrica.setInputPrompt("Informe o tipo...");
            cmbTipoRubrica.setContainerDataSource(getBcTipoRubrica());
            cmbTipoRubrica.setWidth(100, Unit.PERCENTAGE);
            cmbTipoRubrica.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
            cmbTipoRubrica.setItemCaptionPropertyId("tipoRubrica");
            cmbTipoRubrica.setImmediate(true);
            cmbTipoRubrica.setRequired(true);
        }
        return cmbTipoRubrica;
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
            setDado(new Rubrica());
        }
        if (getTxtId().getValue() != null && !getTxtId().getValue().isEmpty()) {
            getDado().setId(Integer.parseInt(getTxtId().getValue()));
        }
        getDado().setRubrica(getTxtRubrica().getValue());
        getDado().setRubricaResumida(getTxtRubricaResumida().getValue());
        getDado().setDescricao(getTxtDescricao().getValue());
        getDado().setAtivo(getCkAtivo().getValue());
        if (getCmbTipoRubrica().getValue() != null) {
            getDado().setTipoRubrica(getBcTipoRubrica().getItem(getCmbTipoRubrica().getValue()).getBean());
        } else {
            getDado().setTipoRubrica(null);
        }

        try {
            rubricaService.save(getDado());
            QGSUI.showInfo("Dados salvos com sucesso.");
            sendRefresh();
            close();

        } catch (Exception ex) {
            QGSUI.doCatch(ex);
        }
    }

    @Override
    protected void doInitiate() {
        getBcTipoRubrica().addAll(listAllService.findAll(TipoRubrica.class));
    }

    @Override
    protected Class<RubricaView> getSendRefreshClazz() {
        return RubricaView.class;
    }

}
