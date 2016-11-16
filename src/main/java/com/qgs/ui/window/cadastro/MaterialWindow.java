package com.qgs.ui.window.cadastro;

import com.qgs.model.cadastro.Material;
import com.qgs.model.cadastro.TipoMaterial;
import com.qgs.service.ListAllService;
import com.qgs.service.cadastro.MaterialService;
import com.qgs.ui.QGSUI;
import com.qgs.ui.view.cadastro.MaterialView;
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
public class MaterialWindow extends BaseWindow<Integer, Material> {

    private TextField txtMaterial;
    private TextArea txtDescricao;
    private CheckBox ckAtivo;
    private BeanContainer<Integer, TipoMaterial> bcTipoMaterial;
    private ComboBox cmbTipoMaterial;

    @EJB
    private MaterialService matService;
    @EJB
    private ListAllService listAllService;

    public MaterialWindow() {
        super("materialwindow", "Gestão de Material");

        FormLayout content = new FormLayout();
        content.setSizeFull();
        content.setSpacing(true);

        content.addComponent(getTxtId());
        content.addComponent(getTxtMaterial());
        content.addComponent(getTxtDescricao());
        content.addComponent(getCmbTipoMaterial());
        content.addComponent(getCkAtivo());

        VerticalLayout vl = new VerticalLayout(content, getBtSave());

        vl.setComponentAlignment(getBtSave(), Alignment.BOTTOM_RIGHT);
        vl.setMargin(new MarginInfo(true));

        setContent(vl);
    }

    @Override
    protected void doLoadDados(Integer id) {
        if (id != null && id > 0) {
            setDado(matService.findById(id));

            getTxtId().setValue(getDado().getId() + "");
            getTxtDescricao().setValue(getDado().getDescricao());
            if (getDado().getTipoMaterial() != null) {
                getCmbTipoMaterial().setValue(getDado().getTipoMaterial().getId());
            }
            getTxtMaterial().setValue(getDado().getMaterial());
            getCkAtivo().setValue(getDado().getAtivo());
        }
    }

    private TextField getTxtMaterial() {
        if (txtMaterial == null) {
            txtMaterial = new TextField("Material");
            txtMaterial.setWidth(100, Unit.PERCENTAGE);
            txtMaterial.setRequired(true);
        }
        return txtMaterial;
    }

    private TextArea getTxtDescricao() {
        if (txtDescricao == null) {
            txtDescricao = new TextArea("Descrição");
            txtDescricao.setWidth(100, Unit.PERCENTAGE);
            txtDescricao.setNullRepresentation("");
        }
        return txtDescricao;
    }

    private BeanContainer<Integer, TipoMaterial> getBcTipoMaterial() {
        if (bcTipoMaterial == null) {
            bcTipoMaterial = new BeanContainer<Integer, TipoMaterial>(TipoMaterial.class);
            bcTipoMaterial.setBeanIdResolver((TipoMaterial bean) -> bean.getId());
        }
        return bcTipoMaterial;
    }

    private ComboBox getCmbTipoMaterial() {
        if (cmbTipoMaterial == null) {
            cmbTipoMaterial = new ComboBox("Tipo material");
            cmbTipoMaterial.setInputPrompt("Informe o tipo...");
            cmbTipoMaterial.setContainerDataSource(getBcTipoMaterial());
            cmbTipoMaterial.setWidth(100, Unit.PERCENTAGE);
            cmbTipoMaterial.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
            cmbTipoMaterial.setItemCaptionPropertyId("tipoMaterial");
            cmbTipoMaterial.setImmediate(true);
            cmbTipoMaterial.setRequired(true);
        }
        return cmbTipoMaterial;
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
            setDado(new Material());
        }
        if (getTxtId().getValue() != null && !getTxtId().getValue().isEmpty()) {
            getDado().setId(Integer.parseInt(getTxtId().getValue()));
        }
        getDado().setMaterial(getTxtMaterial().getValue());
        getDado().setDescricao(getTxtDescricao().getValue());
        getDado().setAtivo(getCkAtivo().getValue());
        if (getCmbTipoMaterial().getValue() != null) {
            getDado().setTipoMaterial(getBcTipoMaterial().getItem(getCmbTipoMaterial().getValue()).getBean());
        } else {
            getDado().setTipoMaterial(null);
        }

        try {
            matService.save(getDado());
            QGSUI.showInfo("Dados salvos com sucesso.");
            sendRefresh();
            close();

        } catch (Exception ex) {
            QGSUI.doCatch(ex);
        }
    }

    @Override
    protected void doInitiate() {
        getBcTipoMaterial().addAll(listAllService.findAll(TipoMaterial.class));
    }

    @Override
    protected Class<MaterialView> getSendRefreshClazz() {
        return MaterialView.class;
    }

}
