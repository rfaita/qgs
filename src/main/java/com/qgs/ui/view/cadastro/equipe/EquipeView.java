package com.qgs.ui.view.cadastro.equipe;

import com.qgs.model.Empresa;
import com.qgs.model.cadastro.equipe.Equipe;
import com.qgs.model.cadastro.equipe.TipoEquipe;
import com.qgs.service.ListAllService;
import com.qgs.service.cadastro.equipe.EquipeService;
import com.qgs.ui.view.base.BaseView;
import com.qgs.ui.window.base.BaseWindow;
import com.qgs.ui.window.cadastro.equipe.EquipeWindow;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.ui.*;
import javax.ejb.EJB;
import javax.inject.Inject;

@CDIView(value = EquipeView.VIEW_ID)
public final class EquipeView extends BaseView<Long, Equipe> {

    public static final String VIEW_ID = "equipe";

    private CheckBox ckInativos;
    private BeanContainer<Integer, TipoEquipe> bcTipoEquipe;
    private ComboBox cmbTipoEquipe;

    @EJB
    private EquipeService matService;
    @EJB
    private ListAllService listAllService;
    @Inject
    private EquipeWindow matWindow;

    public EquipeView() {
        super("equipe", "Cadastro de Equipe");

        getTable().addGeneratedColumn("tipoEquipe", (Table source, Object itemId, Object columnId) -> {
            Property<TipoEquipe> prop = source.getItem(itemId).getItemProperty(columnId);
            if (prop.getType().equals(TipoEquipe.class)) {
                return prop.getValue().getTipoEquipe();
            }
            return "";
        });

    }

    private CheckBox getCkInativos() {
        if (ckInativos == null) {
            ckInativos = new CheckBox("Inativos?");
            ckInativos.addValueChangeListener((Property.ValueChangeEvent event) -> {
                EquipeView.this.doLoadDados();
            });
        }
        return ckInativos;
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
            cmbTipoEquipe = new ComboBox();
            cmbTipoEquipe.setInputPrompt("Tipo equipe");
            cmbTipoEquipe.setContainerDataSource(getBcTipoEquipe());
            cmbTipoEquipe.setWidth(100, Unit.PERCENTAGE);
            cmbTipoEquipe.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
            cmbTipoEquipe.setItemCaptionPropertyId("tipoEquipe");
            cmbTipoEquipe.setImmediate(true);
            cmbTipoEquipe.addValueChangeListener((Property.ValueChangeEvent event) -> {
                doLoadDados();
            });
        }
        return cmbTipoEquipe;
    }

    @Override
    protected Class<Equipe> getClazz() {
        return Equipe.class;
    }

    @Override
    protected String getColumnExpand() {
        return "equipe";
    }

    @Override
    protected String[] getColumns() {
        return new String[]{"id", "equipe", "tipoEquipe"};
    }

    @Override
    protected String[] getColumnsLabels() {
        return new String[]{"Código", "Equipe", "Tipo Equipe"};
    }

    @Override
    protected Component[] getExtraToolbarComponents() {
        return new Component[]{getCmbTipoEquipe(), getCkInativos()};
    }

    @Override
    protected void doLoadDados(Empresa e) {

        if (getBcTipoEquipe().size() <= 0) {
            getBcTipoEquipe().addAll(listAllService.findAll(TipoEquipe.class));
        }

        getContainer().removeAllItems();

        Equipe oTemp = new Equipe();
        oTemp.setEquipe(getTxtFilter().getValue());
        oTemp.setAtivo(!getCkInativos().getValue());
        if (getCmbTipoEquipe().getValue() != null) {
            oTemp.setTipoEquipe(getBcTipoEquipe().getItem(getCmbTipoEquipe().getValue()).getBean());
        } else {
            oTemp.setTipoEquipe(null);
        }

        if (e != null) {
            oTemp.setEmpresa(e);
        }

        getContainer().addAll(matService.findAllByEquipe(oTemp));
    }

    @Override
    protected BaseWindow<Long, Equipe> getBaseWindow() {
        return matWindow;
    }

}
