package com.qgs.ui.window.cadastro.equipe;

import com.qgs.model.cadastro.depto.Departamento;
import com.qgs.model.cadastro.equipe.Equipe;
import com.qgs.model.cadastro.equipe.TipoEquipe;
import com.qgs.service.ListAllService;
import com.qgs.service.cadastro.equipe.EquipeService;
import com.qgs.service.secutiry.SecurityUtils;
import com.qgs.ui.QGSUI;
import com.qgs.ui.view.cadastro.equipe.EquipeView;
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
public class EquipeWindow extends BaseWindow<Long, Equipe> {

    private TextField txtEquipe;
    private CheckBox ckAtivo;
    private BeanContainer<Integer, Departamento> bcDepartamento;
    private ComboBox cmbDepartamento;
    private BeanContainer<Integer, TipoEquipe> bcTipoEquipe;
    private ComboBox cmbTipoEquipe;

    @EJB
    private EquipeService equipeService;
    @EJB
    private ListAllService listAllService;
    @EJB
    private SecurityUtils securityUtils;

    public EquipeWindow() {
        super("equipewindow", "Gestão de Equipe");

        FormLayout content = new FormLayout();
        content.setSizeFull();
        content.setSpacing(true);

        content.addComponent(getTxtId());
        content.addComponent(getTxtEquipe());
        content.addComponent(getCmbDepartamento());
        content.addComponent(getCmbTipoEquipe());
        content.addComponent(getCkAtivo());

        VerticalLayout vl = new VerticalLayout(content, getBtSave());

        vl.setComponentAlignment(getBtSave(), Alignment.BOTTOM_RIGHT);
        vl.setMargin(new MarginInfo(true));

        setContent(vl);
    }

    @Override
    protected void doLoadDados(Long id) {
        if (id != null && id > 0) {
            setDado(equipeService.findById(id));

            getTxtId().setValue(getDado().getId() + "");
            if (getDado().getTipoEquipe() != null) {
                getCmbTipoEquipe().setValue(getDado().getTipoEquipe().getId());
            }
            if (getDado().getDepartamento() != null) {
                getCmbDepartamento().setValue(getDado().getDepartamento().getId());
            }
            getTxtEquipe().setValue(getDado().getEquipe());
            getCkAtivo().setValue(getDado().getAtivo());
        }
    }

    private TextField getTxtEquipe() {
        if (txtEquipe == null) {
            txtEquipe = new TextField("Equipe");
            txtEquipe.setWidth(100, Unit.PERCENTAGE);
            txtEquipe.setRequired(true);
        }
        return txtEquipe;
    }

    private BeanContainer<Integer, Departamento> getBcDepartamento() {
        if (bcDepartamento == null) {
            bcDepartamento = new BeanContainer<Integer, Departamento>(Departamento.class);
            bcDepartamento.setBeanIdResolver((Departamento bean) -> bean.getId());
        }
        return bcDepartamento;
    }

    private ComboBox getCmbDepartamento() {
        if (cmbDepartamento == null) {
            cmbDepartamento = new ComboBox("Departamento");
            cmbDepartamento.setInputPrompt("Informe o departamento...");
            cmbDepartamento.setContainerDataSource(getBcDepartamento());
            cmbDepartamento.setWidth(100, Unit.PERCENTAGE);
            cmbDepartamento.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
            cmbDepartamento.setItemCaptionPropertyId("departamento");
            cmbDepartamento.setImmediate(true);
            cmbDepartamento.setRequired(true);
        }
        return cmbDepartamento;
    }

    private BeanContainer<Integer, TipoEquipe> getBcTipoEquipe() {
        if (bcTipoEquipe == null) {
            bcTipoEquipe = new BeanContainer<Integer, TipoEquipe>(TipoEquipe.class);
            bcTipoEquipe.setBeanIdResolver((TipoEquipe bean) -> bean.getId());
        }
        return bcTipoEquipe;
    }

    private ComboBox getCmbTipoEquipe() {
        if (cmbTipoEquipe == null) {
            cmbTipoEquipe = new ComboBox("Tipo equipe");
            cmbTipoEquipe.setInputPrompt("Informe o tipo...");
            cmbTipoEquipe.setContainerDataSource(getBcTipoEquipe());
            cmbTipoEquipe.setWidth(100, Unit.PERCENTAGE);
            cmbTipoEquipe.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
            cmbTipoEquipe.setItemCaptionPropertyId("tipoEquipe");
            cmbTipoEquipe.setImmediate(true);
            cmbTipoEquipe.setRequired(true);
        }
        return cmbTipoEquipe;
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
            setDado(new Equipe());
        }
        if (getTxtId().getValue() != null && !getTxtId().getValue().isEmpty()) {
            getDado().setId(Long.parseLong(getTxtId().getValue()));
        }
        getDado().setEquipe(getTxtEquipe().getValue());
        getDado().setAtivo(getCkAtivo().getValue());
        if (getCmbTipoEquipe().getValue() != null) {
            getDado().setTipoEquipe(getBcTipoEquipe().getItem(getCmbTipoEquipe().getValue()).getBean());
        } else {
            getDado().setTipoEquipe(null);
        }
        if (getCmbDepartamento().getValue() != null) {
            getDado().setDepartamento(getBcDepartamento().getItem(getCmbDepartamento().getValue()).getBean());
        } else {
            getDado().setDepartamento(null);
        }

        try {
            equipeService.save(getDado());
            QGSUI.showInfo("Dados salvos com sucesso.");
            sendRefresh();
            close();

        } catch (Exception ex) {
            QGSUI.doCatch(ex);
        }
    }

    @Override
    protected void doInitiate() {
        getBcTipoEquipe().addAll(listAllService.findAll(TipoEquipe.class));
        getBcDepartamento().addAll(listAllService.findAllByParam(Departamento.class, "idEmpresa", securityUtils.getEmpresa().getId()));
    }

    @Override
    protected Class<EquipeView> getSendRefreshClazz() {
        return EquipeView.class;
    }

}
