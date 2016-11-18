package com.qgs.ui.view.cadastro.servico;

import com.qgs.model.Empresa;
import com.qgs.model.cadastro.servico.Servico;
import com.qgs.model.cadastro.servico.TipoServico;
import com.qgs.service.ListAllService;
import com.qgs.service.cadastro.servico.ServicoService;
import com.qgs.ui.view.base.BaseView;
import com.qgs.ui.window.base.BaseWindow;
import com.qgs.ui.window.cadastro.servico.ServicoWindow;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import javax.ejb.EJB;
import javax.inject.Inject;

@CDIView(value = ServicoView.VIEW_ID)
public final class ServicoView extends BaseView<Long, Servico> {

    public static final String VIEW_ID = "servico";

    private CheckBox ckInativos;
    private BeanContainer<Integer, TipoServico> bcTipoServico;
    private ComboBox cmbTipoServico;

    @EJB
    private ServicoService servicoService;
    @EJB
    private ListAllService listAllService;
    @Inject
    private ServicoWindow servicoWindow;

    public ServicoView() {
        super("servico", FontAwesome.ARCHIVE, "Cadastro de Serviço");

        getTable().addGeneratedColumn("tipoServico", (Table source, Object itemId, Object columnId) -> {
            Property<TipoServico> prop = source.getItem(itemId).getItemProperty(columnId);
            if (prop.getType().equals(TipoServico.class)) {
                return prop.getValue().getTipoServico();
            }
            return "";
        });

    }

    private CheckBox getCkInativos() {
        if (ckInativos == null) {
            ckInativos = new CheckBox("Inativos?");
            ckInativos.addValueChangeListener((Property.ValueChangeEvent event) -> {
                ServicoView.this.doLoadDados();
            });
        }
        return ckInativos;
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
            cmbTipoServico = new ComboBox();
            cmbTipoServico.setInputPrompt("Tipo serviço");
            cmbTipoServico.setContainerDataSource(getBcTipoServico());
            cmbTipoServico.setWidth(100, Unit.PERCENTAGE);
            cmbTipoServico.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
            cmbTipoServico.setItemCaptionPropertyId("tipoServico");
            cmbTipoServico.setImmediate(true);
            cmbTipoServico.addValueChangeListener((Property.ValueChangeEvent event) -> {
                doLoadDados();
            });
        }
        return cmbTipoServico;
    }

    @Override
    protected Class<Servico> getClazz() {
        return Servico.class;
    }

    @Override
    protected String getColumnExpand() {
        return "servico";
    }

    @Override
    protected String[] getColumns() {
        return new String[]{"id", "servico", "tipoServico"};
    }

    @Override
    protected String[] getColumnsLabels() {
        return new String[]{"Código", "Servico", "Tipo Servico"};
    }

    @Override
    protected Component[] getExtraToolbarComponents() {
        return new Component[]{getCmbTipoServico(), getCkInativos()};
    }

    @Override
    protected void doLoadDados(Empresa e) {

        if (getBcTipoServico().size() <= 0) {
            getBcTipoServico().addAll(listAllService.findAll(TipoServico.class));
        }

        getContainer().removeAllItems();

        Servico oTemp = new Servico();
        oTemp.setServico(getTxtFilter().getValue());
        oTemp.setAtivo(!getCkInativos().getValue());
        if (getCmbTipoServico().getValue() != null) {
            oTemp.setTipoServico(getBcTipoServico().getItem(getCmbTipoServico().getValue()).getBean());
        } else {
            oTemp.setTipoServico(null);
        }

        if (e != null) {
            oTemp.setEmpresa(e);
        }

        getContainer().addAll(servicoService.findAllByServico(oTemp));
    }

    @Override
    protected BaseWindow<Long, Servico> getBaseWindow() {
        return servicoWindow;
    }

}
