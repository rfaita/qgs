package com.qgs.ui.window.setor;

import com.qgs.model.cadastro.servico.TipoServico;
import com.qgs.model.cadastro.setor.Setor;
import com.qgs.model.cadastro.setor.SetorCriterio;
import com.qgs.model.endereco.Bairro;
import com.qgs.model.endereco.Cidade;
import com.qgs.model.endereco.Logradouro;
import com.qgs.model.endereco.Pais;
import com.qgs.model.endereco.UF;
import com.qgs.service.ListAllService;
import com.qgs.service.setor.SetorService;
import com.qgs.ui.QGSUI;
import com.qgs.ui.view.setor.SetorView;
import com.qgs.ui.window.base.BaseWindow;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author rafael
 */
@RequestScoped
public class SetorWindow extends BaseWindow<Integer, Setor> {

    private TextField txtSetor;
    private CheckBox ckAtivo;
    private BeanContainer<Integer, TipoServico> bcTipoServico;
    private ComboBox cmbTipoServico;
    private BeanContainer<Integer, SetorCriterio> bcSetorCriterio;
    private Table tbSetorCriterio;

    private BeanContainer<Integer, Pais> bcPais;
    private ComboBox cmbPais;
    private BeanContainer<Integer, UF> bcUF;
    private ComboBox cmbUF;
    private BeanContainer<Integer, Cidade> bcCidade;
    private ComboBox cmbCidade;
    private BeanContainer<Integer, Bairro> bcBairro;
    private ComboBox cmbBairro;
    private BeanContainer<Integer, Logradouro> bcLogradouro;
    private ComboBox cmbLogradouro;

    private Button btAdd;
    private Button btDel;
    private Button btLimpar;

    private Integer index = -1;

    @EJB
    private SetorService matService;
    @EJB
    private ListAllService listAllService;

    public SetorWindow() {
        super("setorwindow", "Gestão de Setores de Serviço");

        FormLayout content = new FormLayout();
        content.setSizeFull();
        content.setSpacing(true);

        content.addComponent(getTxtId());
        content.addComponent(getTxtSetor());
        content.addComponent(getCmbTipoServico());
        content.addComponent(getCkAtivo());
        content.addComponent(getCmbPais());
        content.addComponent(getCmbUF());
        content.addComponent(getCmbCidade());
        content.addComponent(getCmbBairro());
        content.addComponent(getCmbLogradouro());

        HorizontalLayout h = new HorizontalLayout(getBtAdd(), getBtDel(), getBtLimpar());
        h.setWidth(100, Unit.PERCENTAGE);

        content.addComponent(h);

        content.addComponent(getTbSetorCriterio());

        VerticalLayout vl = new VerticalLayout(content, getBtSave());

        vl.setComponentAlignment(getBtSave(), Alignment.BOTTOM_RIGHT);
        vl.setMargin(new MarginInfo(true));

        setContent(vl);

        setWidth(800, Unit.PIXELS);

    }

    @Override
    protected void doLoadDados(Integer id) {
        if (id != null && id > 0) {
            setDado(matService.findCompleteById(id));

            getTxtId().setValue(getDado().getId() + "");
            if (getDado().getTipoServicoAtendido() != null) {
                getCmbTipoServico().setValue(getDado().getTipoServicoAtendido().getId());
            }
            getTxtSetor().setValue(getDado().getSetor());
            getCkAtivo().setValue(getDado().getAtivo());
            getBcSetorCriterio().addAll(getDado().getCriterios());

        }
    }

    private TextField getTxtSetor() {
        if (txtSetor == null) {
            txtSetor = new TextField("Setor");
            txtSetor.setWidth(100, Unit.PERCENTAGE);
            txtSetor.setRequired(true);
        }
        return txtSetor;
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

    private BeanContainer<Integer, Pais> getBcPais() {
        if (bcPais == null) {
            bcPais = new BeanContainer<Integer, Pais>(Pais.class);
            bcPais.setBeanIdResolver((Pais bean) -> bean.getId());
        }
        return bcPais;
    }

    private ComboBox getCmbPais() {
        if (cmbPais == null) {
            cmbPais = new ComboBox("Critério país");
            cmbPais.setInputPrompt("Informe o país...");
            cmbPais.setContainerDataSource(getBcPais());
            cmbPais.setWidth(100, Unit.PERCENTAGE);
            cmbPais.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
            cmbPais.setItemCaptionPropertyId("pais");
            cmbPais.setImmediate(true);
            cmbPais.addValueChangeListener((Property.ValueChangeEvent event) -> {
                if (cmbPais.getValue() != null) {
                    doLoadUF();
                }
            });
        }
        return cmbPais;
    }

    private BeanContainer<Integer, UF> getBcUF() {
        if (bcUF == null) {
            bcUF = new BeanContainer<Integer, UF>(UF.class);
            bcUF.setBeanIdResolver((UF bean) -> bean.getId());
        }
        return bcUF;
    }

    private ComboBox getCmbUF() {
        if (cmbUF == null) {
            cmbUF = new ComboBox("Critério estado");
            cmbUF.setInputPrompt("Informe o estado...");
            cmbUF.setContainerDataSource(getBcUF());
            cmbUF.setWidth(100, Unit.PERCENTAGE);
            cmbUF.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
            cmbUF.setItemCaptionPropertyId("descricao");
            cmbUF.setImmediate(true);
            cmbUF.addValueChangeListener((Property.ValueChangeEvent event) -> {
                if (cmbUF.getValue() != null) {
                    doLoadCidade();
                }
            });
        }
        return cmbUF;
    }

    private BeanContainer<Integer, Cidade> getBcCidade() {
        if (bcCidade == null) {
            bcCidade = new BeanContainer<Integer, Cidade>(Cidade.class);
            bcCidade.setBeanIdResolver((Cidade bean) -> bean.getId());
        }
        return bcCidade;
    }

    private ComboBox getCmbCidade() {
        if (cmbCidade == null) {
            cmbCidade = new ComboBox("Critério cidade");
            cmbCidade.setInputPrompt("Informe o cidade...");
            cmbCidade.setContainerDataSource(getBcCidade());
            cmbCidade.setWidth(100, Unit.PERCENTAGE);
            cmbCidade.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
            cmbCidade.setItemCaptionPropertyId("cidade");
            cmbCidade.setImmediate(true);
            cmbCidade.addValueChangeListener((Property.ValueChangeEvent event) -> {
                if (cmbCidade.getValue() != null) {
                    doLoadBairro();
                }
            });
        }
        return cmbCidade;
    }

    private BeanContainer<Integer, Bairro> getBcBairro() {
        if (bcBairro == null) {
            bcBairro = new BeanContainer<Integer, Bairro>(Bairro.class);
            bcBairro.setBeanIdResolver((Bairro bean) -> bean.getId());
        }
        return bcBairro;
    }

    private ComboBox getCmbBairro() {
        if (cmbBairro == null) {
            cmbBairro = new ComboBox("Critério bairro");
            cmbBairro.setInputPrompt("Informe o bairro...");
            cmbBairro.setContainerDataSource(getBcBairro());
            cmbBairro.setWidth(100, Unit.PERCENTAGE);
            cmbBairro.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
            cmbBairro.setItemCaptionPropertyId("bairro");
            cmbBairro.setImmediate(true);
            cmbBairro.addValueChangeListener((Property.ValueChangeEvent event) -> {
                if (cmbBairro.getValue() != null) {
                    doLoadLogradouro();
                }
            });
        }
        return cmbBairro;
    }

    private BeanContainer<Integer, Logradouro> getBcLogradouro() {
        if (bcLogradouro == null) {
            bcLogradouro = new BeanContainer<Integer, Logradouro>(Logradouro.class);
            bcLogradouro.setBeanIdResolver((Logradouro bean) -> bean.getId());
        }
        return bcLogradouro;
    }

    private ComboBox getCmbLogradouro() {
        if (cmbLogradouro == null) {
            cmbLogradouro = new ComboBox("Critério logradouro");
            cmbLogradouro.setInputPrompt("Informe o logradouro...");
            cmbLogradouro.setContainerDataSource(getBcPais());
            cmbLogradouro.setWidth(100, Unit.PERCENTAGE);
            cmbLogradouro.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
            cmbLogradouro.setItemCaptionPropertyId("logradouro");
            cmbLogradouro.setImmediate(true);
        }
        return cmbLogradouro;
    }

    private CheckBox getCkAtivo() {
        if (ckAtivo == null) {
            ckAtivo = new CheckBox("Ativo?");
        }
        return ckAtivo;
    }

    private BeanContainer<Integer, SetorCriterio> getBcSetorCriterio() {
        if (bcSetorCriterio == null) {
            bcSetorCriterio = new BeanContainer<Integer, SetorCriterio>(SetorCriterio.class);
            bcSetorCriterio.setBeanIdResolver((SetorCriterio bean) -> bean.getId());
        }
        return bcSetorCriterio;
    }

    private Table getTbSetorCriterio() {
        if (tbSetorCriterio == null) {
            tbSetorCriterio = new Table();
            tbSetorCriterio.setWidth(100, Unit.PERCENTAGE);
            tbSetorCriterio.setHeight(200, Unit.PIXELS);
            tbSetorCriterio.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES);
            tbSetorCriterio.addStyleName(ValoTheme.TABLE_COMPACT);
            tbSetorCriterio.setSelectable(true);

            tbSetorCriterio.setColumnCollapsingAllowed(true);
            tbSetorCriterio.setColumnReorderingAllowed(true);
            tbSetorCriterio.setContainerDataSource(getBcSetorCriterio());
            tbSetorCriterio.setSortAscending(false);

            tbSetorCriterio.addGeneratedColumn("paisCriterio", (Table source, Object itemId, Object columnId) -> {
                Property<Pais> prop = source.getItem(itemId).getItemProperty(columnId);
                if (prop.getType().equals(Pais.class)) {
                    return prop.getValue().getPais();
                }
                return "";
            });
            tbSetorCriterio.addGeneratedColumn("ufCriterio", (Table source, Object itemId, Object columnId) -> {
                Property<UF> prop = source.getItem(itemId).getItemProperty(columnId);
                if (prop.getType().equals(UF.class) && prop.getValue() != null) {
                    return prop.getValue().getUf();
                }
                return "";
            });
            tbSetorCriterio.addGeneratedColumn("cidadeCriterio", (Table source, Object itemId, Object columnId) -> {
                Property<Cidade> prop = source.getItem(itemId).getItemProperty(columnId);
                if (prop.getType().equals(Cidade.class) && prop.getValue() != null) {
                    return prop.getValue().getCidade();
                }
                return "";
            });
            tbSetorCriterio.addGeneratedColumn("bairroCriterio", (Table source, Object itemId, Object columnId) -> {
                Property<Bairro> prop = source.getItem(itemId).getItemProperty(columnId);
                if (prop.getType().equals(Bairro.class) && prop.getValue() != null) {
                    return prop.getValue().getBairro();
                }
                return "";
            });
            tbSetorCriterio.addGeneratedColumn("logradouroCriterio", (Table source, Object itemId, Object columnId) -> {
                Property<Logradouro> prop = source.getItem(itemId).getItemProperty(columnId);
                if (prop.getType().equals(Logradouro.class) && prop.getValue() != null) {
                    return prop.getValue().getLogradouro();
                }
                return "";
            });

            tbSetorCriterio.setVisibleColumns("paisCriterio", "ufCriterio", "cidadeCriterio", "bairroCriterio", "logradouroCriterio");
            tbSetorCriterio.setColumnHeaders("Páis", "Estado", "Cidade", "Bairro", "Logradouro");

            tbSetorCriterio.setImmediate(true);
        }

        return tbSetorCriterio;
    }

    private Button getBtAdd() {
        if (btAdd == null) {
            btAdd = new Button("Adicionar Critério");
            btAdd.setIcon(FontAwesome.PLUS);
            btAdd.addClickListener((Button.ClickEvent event) -> {
                if (getCmbPais().getValue() == null) {
                    QGSUI.showWarn("Informe algum critério.");
                    return;
                }
                SetorCriterio criterio = new SetorCriterio();
                criterio.setId(index--);
                if (getCmbPais().getValue() != null) {
                    criterio.setPaisCriterio(getBcPais().getItem(getCmbPais().getValue()).getBean());
                } else {
                    criterio.setPaisCriterio(null);
                }
                if (getCmbUF().getValue() != null) {
                    criterio.setUfCriterio(getBcUF().getItem(getCmbUF().getValue()).getBean());
                } else {
                    criterio.setUfCriterio(null);
                }
                if (getCmbCidade().getValue() != null) {
                    criterio.setCidadeCriterio(getBcCidade().getItem(getCmbCidade().getValue()).getBean());
                } else {
                    criterio.setCidadeCriterio(null);
                }
                if (getCmbBairro().getValue() != null) {
                    criterio.setBairroCriterio(getBcBairro().getItem(getCmbBairro().getValue()).getBean());
                } else {
                    criterio.setBairroCriterio(null);
                }
                if (getCmbLogradouro().getValue() != null) {
                    criterio.setLogradouroCriterio(getBcLogradouro().getItem(getCmbLogradouro().getValue()).getBean());
                } else {
                    criterio.setLogradouroCriterio(null);
                }

                getBcSetorCriterio().addBean(criterio);
                if (getCmbUF().getValue() == null) {
                    getCmbPais().setValue(null);
                }
                if (getCmbCidade().getValue() == null) {
                    getCmbUF().setValue(null);
                }
                if (getCmbBairro().getValue() == null) {
                    getCmbCidade().setValue(null);
                }
                if (getCmbLogradouro().getValue() == null) {
                    getCmbBairro().setValue(null);
                }
                getCmbLogradouro().setValue(null);

            });
        }
        return btAdd;
    }

    private Button getBtDel() {
        if (btDel == null) {
            btDel = new Button("Remover Critério");
            btDel.setIcon(FontAwesome.MINUS);
            btDel.addClickListener((Button.ClickEvent event) -> {
                getCmbPais().setValue(null);
                getCmbUF().setValue(null);
                getCmbCidade().setValue(null);
                getCmbBairro().setValue(null);
                getCmbLogradouro().setValue(null);
                if (getTbSetorCriterio().getValue() != null) {
                    SetorCriterio o = getBcSetorCriterio().getItem(getTbSetorCriterio().getValue()).getBean();
                    getBcSetorCriterio().removeItem(o.getId());
                } else {
                    QGSUI.showWarn("Selecione o que deseja remover.");
                }

            });
        }
        return btDel;
    }

    private Button getBtLimpar() {
        if (btLimpar == null) {
            btLimpar = new Button("Limpar Critérios");
            btLimpar.setIcon(FontAwesome.REMOVE);
            btLimpar.addClickListener((Button.ClickEvent event) -> {
                if (getTbSetorCriterio().getValue() != null) {
                    SetorCriterio o = getBcSetorCriterio().getItem(getTbSetorCriterio().getValue()).getBean();
                    getBcSetorCriterio().removeItem(o.getId());
                } else {
                    QGSUI.showWarn("Selecione o que deseja remover.");
                }

            });
        }
        return btDel;
    }

    @Override
    protected void doSave() {
        if (getDado() == null) {
            setDado(new Setor());
        }
        if (getTxtId().getValue() != null && !getTxtId().getValue().isEmpty()) {
            getDado().setId(Integer.parseInt(getTxtId().getValue()));
        }
        getDado().setSetor(getTxtSetor().getValue());
        getDado().setAtivo(getCkAtivo().getValue());
        if (getCmbTipoServico().getValue() != null) {
            getDado().setTipoServicoAtendido(getBcTipoServico().getItem(getCmbTipoServico().getValue()).getBean());
        } else {
            getDado().setTipoServicoAtendido(null);
        }

        getDado().setCriterios(new ArrayList<SetorCriterio>());
        for (Integer id : getBcSetorCriterio().getItemIds()) {
            SetorCriterio sc = getBcSetorCriterio().getItem(id).getBean();
            if (sc.getId() != null && sc.getId() < 0) {
                sc.setId(null);
                sc.setSetor(getDado());
            }
            getDado().getCriterios().add(sc);
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
        getBcTipoServico().addAll(listAllService.findAll(TipoServico.class));
        getBcPais().addAll(listAllService.findAll(Pais.class));
    }

    @Override
    protected Class<SetorView> getSendRefreshClazz() {
        return SetorView.class;
    }

    private void doLoadLogradouro() {
        getCmbLogradouro().setValue(null);
        getBcLogradouro().removeAllItems();
        getBcLogradouro().addAll(listAllService.findAllByParam(Logradouro.class, "idBairro", getBcBairro().getItem(getCmbBairro().getValue()).getBean().getId()));
    }

    private void doLoadBairro() {
        getCmbLogradouro().setValue(null);
        getBcBairro().removeAllItems();
        getBcBairro().addAll(listAllService.findAllByParam(Bairro.class, "idCidade", getBcCidade().getItem(getCmbCidade().getValue()).getBean().getId()));
    }

    private void doLoadCidade() {
        getCmbCidade().setValue(null);
        getCmbBairro().setValue(null);
        getCmbLogradouro().setValue(null);
        getBcCidade().removeAllItems();
        getBcCidade().addAll(listAllService.findAllByParam(Cidade.class, "idUf", getBcUF().getItem(getCmbUF().getValue()).getBean().getId()));
    }

    private void doLoadUF() {
        getCmbUF().setValue(null);
        getCmbCidade().setValue(null);
        getCmbBairro().setValue(null);
        getCmbLogradouro().setValue(null);
        getBcUF().removeAllItems();
        getBcUF().addAll(listAllService.findAllByParam(UF.class, "idPais", getBcPais().getItem(getCmbPais().getValue()).getBean().getId()));
    }

}
