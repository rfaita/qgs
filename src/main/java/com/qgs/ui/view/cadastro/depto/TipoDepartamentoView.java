package com.qgs.ui.view.cadastro.depto;

import com.qgs.model.Empresa;
import com.qgs.model.cadastro.depto.TipoDepartamento;
import com.qgs.model.cadastro.servico.TipoServico;
import com.qgs.service.ListAllService;
import com.qgs.service.cadastro.depto.TipoDepartamentoService;
import com.qgs.ui.view.base.BaseView;
import com.qgs.ui.window.base.BaseWindow;
import com.qgs.ui.window.cadastro.depto.TipoDepartamentoWindow;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.ui.*;
import javax.ejb.EJB;
import javax.inject.Inject;

@CDIView(value = TipoDepartamentoView.VIEW_ID)
public final class TipoDepartamentoView extends BaseView<Integer, TipoDepartamento> {

    public static final String VIEW_ID = "tipoDepto";

    private BeanContainer<Integer, TipoServico> bcTipoServico;
    private ComboBox cmbTipoServico;
    private CheckBox ckInativos;

    @EJB
    private TipoDepartamentoService tipoDeptoService;
    @EJB
    private ListAllService listAllService;
    @Inject
    private TipoDepartamentoWindow tipoDeptoWindow;

    public TipoDepartamentoView() {
        super("tipoDepto", "Cadastro de Tipo Departamento");

        getTable().addGeneratedColumn("tipoServicoAtendido", (Table source, Object itemId, Object columnId) -> {
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
                TipoDepartamentoView.this.doLoadDados();
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
            cmbTipoServico.setInputPrompt("Tipo de serviço");
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
    protected Class<TipoDepartamento> getClazz() {
        return TipoDepartamento.class;
    }

    @Override
    protected String getColumnExpand() {
        return "tipoDepartamento";
    }

    @Override
    protected String[] getColumns() {
        return new String[]{"id", "tipoDepartamento", "tipoServicoAtendido", "criterioSelecaoSetor"};
    }

    @Override
    protected String[] getColumnsLabels() {
        return new String[]{"Código", "Tipo Departamento", "Tipo serviço atendido", "Critério de seleção de setor"};
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

        TipoDepartamento oTemp = new TipoDepartamento();
        oTemp.setTipoDepartamento(getTxtFilter().getValue());
        oTemp.setAtivo(!getCkInativos().getValue());
        if (getCmbTipoServico().getValue() != null) {
            oTemp.setTipoServicoAtendido(getBcTipoServico().getItem(getCmbTipoServico().getValue()).getBean());
        } else {
            oTemp.setTipoServicoAtendido(null);
        }

        if (e != null) {
            oTemp.setEmpresa(e);
        }

        getContainer().addAll(tipoDeptoService.findAllByTipoDepartamento(oTemp));
    }

    @Override
    protected BaseWindow<Integer, TipoDepartamento> getBaseWindow() {
        return tipoDeptoWindow;
    }

}
