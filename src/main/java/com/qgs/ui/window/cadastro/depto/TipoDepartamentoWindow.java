package com.qgs.ui.window.cadastro.depto;

import com.qgs.enums.CriterioSelecaoSetorEnum;
import com.qgs.model.cadastro.depto.TipoDepartamento;
import com.qgs.model.cadastro.servico.TipoServico;
import com.qgs.service.ListAllService;
import com.qgs.service.cadastro.depto.TipoDepartamentoService;
import com.qgs.ui.QGSUI;
import com.qgs.ui.view.cadastro.depto.TipoDepartamentoView;
import com.qgs.ui.window.base.BaseWindow;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import java.util.Arrays;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author rafael
 */
@RequestScoped
public class TipoDepartamentoWindow extends BaseWindow<Integer, TipoDepartamento> {

    private TextField txtTipoDepartamento;
    private BeanContainer<Integer, TipoServico> bcTipoServico;
    private ComboBox cmbTipoServico;
    private BeanContainer<CriterioSelecaoSetorEnum, CriterioSelecaoSetorEnum> bcCriterioSelecaoSetor;
    private ComboBox cmbCriterioSelecaoSetor;
    private CheckBox ckAtivo;

    @EJB
    private TipoDepartamentoService tipoDeptoService;
    @EJB
    private ListAllService listAllService;

    public TipoDepartamentoWindow() {
        super("tipodeptowindow", "Gestão de Tipo Departamento");

        FormLayout content = new FormLayout();
        content.setSizeFull();
        content.setMargin(new MarginInfo(true));
        content.setSpacing(true);

        content.addComponent(getTxtId());
        content.addComponent(getTxtTipoDepartamento());
        content.addComponent(getCmbTipoServico());
        content.addComponent(getCmbCriterioSelecaoSetor());
        content.addComponent(getCkAtivo());
        content.addComponent(getBtSave());
        content.setComponentAlignment(getBtSave(), Alignment.BOTTOM_RIGHT);

        setContent(content);

    }

    @Override
    protected void doLoadDados(Integer id) {
        if (id != null && id > 0) {
            setDado(tipoDeptoService.findById(id));

            getTxtId().setValue(getDado().getId() + "");
            getTxtTipoDepartamento().setValue(getDado().getTipoDepartamento());
            if (getDado().getTipoServicoAtendido() != null) {
                getCmbTipoServico().setValue(getDado().getTipoServicoAtendido().getId());
            }
            if (getDado().getCriterioSelecaoSetor() != null) {
                getCmbCriterioSelecaoSetor().setValue(getDado().getCriterioSelecaoSetor());
            }
            getCkAtivo().setValue(getDado().getAtivo());
        }
    }

    private TextField getTxtTipoDepartamento() {
        if (txtTipoDepartamento == null) {
            txtTipoDepartamento = new TextField("TipoDepartamento");
            txtTipoDepartamento.setWidth(100, Unit.PERCENTAGE);
            txtTipoDepartamento.setRequired(true);
        }
        return txtTipoDepartamento;
    }

    private CheckBox getCkAtivo() {
        if (ckAtivo == null) {
            ckAtivo = new CheckBox("Ativo?");
        }
        return ckAtivo;
    }

    private BeanContainer<Integer, TipoServico> getBcTipoServico() {
        if (bcTipoServico == null) {
            bcTipoServico = new BeanContainer<Integer, TipoServico>(TipoServico.class);
            bcTipoServico.setBeanIdResolver((TipoServico bean) -> bean.getId());
        }
        return bcTipoServico;
    }

    private ComboBox getCmbTipoServico() {
        if (cmbTipoServico == null) {
            cmbTipoServico = new ComboBox("Tipo serviço");
            cmbTipoServico.setInputPrompt("Informe o tipo...");
            cmbTipoServico.setContainerDataSource(getBcTipoServico());
            cmbTipoServico.setWidth(100, Unit.PERCENTAGE);
            cmbTipoServico.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
            cmbTipoServico.setItemCaptionPropertyId("tipoServico");
            cmbTipoServico.setImmediate(true);
            cmbTipoServico.setRequired(true);
        }
        return cmbTipoServico;
    }

    private BeanContainer<CriterioSelecaoSetorEnum, CriterioSelecaoSetorEnum> getBcCriterioSelecaoSetor() {
        if (bcCriterioSelecaoSetor == null) {
            bcCriterioSelecaoSetor = new BeanContainer<CriterioSelecaoSetorEnum, CriterioSelecaoSetorEnum>(CriterioSelecaoSetorEnum.class);
            bcCriterioSelecaoSetor.setBeanIdResolver((CriterioSelecaoSetorEnum bean) -> bean);
        }
        return bcCriterioSelecaoSetor;
    }

    private ComboBox getCmbCriterioSelecaoSetor() {
        if (cmbCriterioSelecaoSetor == null) {
            cmbCriterioSelecaoSetor = new ComboBox("Critério de seleção de setor");
            cmbCriterioSelecaoSetor.setInputPrompt("Informe o critério...");
            cmbCriterioSelecaoSetor.setContainerDataSource(getBcCriterioSelecaoSetor());
            cmbCriterioSelecaoSetor.setWidth(100, Unit.PERCENTAGE);
            cmbCriterioSelecaoSetor.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
            cmbCriterioSelecaoSetor.setItemCaptionPropertyId("criterio");
            cmbCriterioSelecaoSetor.setImmediate(true);
            cmbCriterioSelecaoSetor.setRequired(true);
        }
        return cmbCriterioSelecaoSetor;
    }

    @Override
    protected void doSave() {
        if (getDado() == null) {
            setDado(new TipoDepartamento());
        }
        if (getTxtId().getValue() != null && !getTxtId().getValue().isEmpty()) {
            getDado().setId(Integer.parseInt(getTxtId().getValue()));
        }
        getDado().setTipoDepartamento(getTxtTipoDepartamento().getValue());
        getDado().setAtivo(getCkAtivo().getValue());
        if (getCmbTipoServico().getValue() != null) {
            getDado().setTipoServicoAtendido(getBcTipoServico().getItem(getCmbTipoServico().getValue()).getBean());
        } else {
            getDado().setTipoServicoAtendido(null);
        }
        if (getCmbCriterioSelecaoSetor().getValue() != null) {
            getDado().setCriterioSelecaoSetor(getBcCriterioSelecaoSetor().getItem(getCmbCriterioSelecaoSetor().getValue()).getBean());
        } else {
            getDado().setCriterioSelecaoSetor(null);
        }

        try {
            tipoDeptoService.save(getDado());
            QGSUI.showInfo("Dados salvos com sucesso.");
            sendRefresh();
            close();

        } catch (Exception ex) {
            QGSUI.doCatch(ex);
        }
    }

    @Override
    protected void doInitiate() {
        getBcTipoServico().addAll(listAllService.findAll(TipoServico.class));
        getBcCriterioSelecaoSetor().addAll(Arrays.asList(CriterioSelecaoSetorEnum.values()));
    }

    @Override
    protected Class<TipoDepartamentoView> getSendRefreshClazz() {
        return TipoDepartamentoView.class;
    }

}
