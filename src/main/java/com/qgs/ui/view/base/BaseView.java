package com.qgs.ui.view.base;

import com.qgs.model.BaseBean;
import com.qgs.model.Empresa;
import com.qgs.ui.QGSUI;
import com.qgs.ui.broadcast.BroadcastMessage;
import com.qgs.ui.view.RefreshView;
import com.qgs.ui.view.ResizeView;
import com.qgs.ui.window.base.BaseWindow;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author rafael
 * @param <ID>
 * @param <T>
 */
public abstract class BaseView<ID, T extends BaseBean<ID>> extends VerticalLayout implements View, RefreshView, ResizeView {

    private Table table;
    private BeanContainer<ID, T> container;
    private Button btnAdd;
    private Button btnEdit;
    private TextField txtFilter;

    private String[] defaultCollapsible = {};

    public BaseView(String styleView, Resource icon, String title) {
        this(styleView, icon, title, new String[]{});
    }

    public BaseView(String styleView, Resource icon, String title, String[] defaultCollapsible) {
        setSizeFull();
        addStyleName(styleView);
        addStyleName("view");

        addComponent(getToolbar(icon, title));

        addComponent(getTable());
        setExpandRatio(getTable(), 1);

        this.defaultCollapsible = defaultCollapsible;

        doOnResize();

    }

    private HorizontalLayout getToolbar(Resource icon, String t) {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);
        Responsive.makeResponsive(header);

        Label title = new Label(t);
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(title);

        Component[] extraToolbarComponents = getExtraToolbarComponents();

        Component[] componentsToolBar = new Component[extraToolbarComponents.length + 3];
        componentsToolBar[0] = getTxtFilter();
        int i = 1;
        for (Component c : extraToolbarComponents) {
            componentsToolBar[i++] = c;
        }
        componentsToolBar[i] = getBtnAdd();
        componentsToolBar[i + 1] = getBtnEdit();

        HorizontalLayout tools = new HorizontalLayout(componentsToolBar);
        tools.setSpacing(true);
        tools.addStyleName("toolbar");

        header.addComponent(tools);

        return header;
    }

    protected BeanContainer<ID, T> getContainer() {
        if (container == null) {
            container = new BeanContainer<ID, T>(getClazz());
            container.setBeanIdResolver((T bean) -> bean.getId());
        }
        return container;
    }

    protected Table getTable() {
        if (table == null) {
            table = new Table();
            table.setSizeFull();
            table.addStyleName(ValoTheme.TABLE_BORDERLESS);
            table.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES);
            table.addStyleName(ValoTheme.TABLE_COMPACT);
            table.setSelectable(true);

            table.setColumnCollapsingAllowed(true);
            table.setColumnReorderingAllowed(true);
            table.setContainerDataSource(getContainer());
            table.setSortAscending(false);

            table.setVisibleColumns(getColumns());
            table.setColumnExpandRatio(getColumnExpand(), 1);
            table.setColumnHeaders(getColumnsLabels());

            table.setImmediate(true);
        }

        return table;
    }

    protected TextField getTxtFilter() {
        if (txtFilter == null) {
            txtFilter = new TextField();
            txtFilter.setInputPrompt("Procurar");
            txtFilter.setIcon(FontAwesome.SEARCH);
            txtFilter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
            txtFilter.addValueChangeListener((Property.ValueChangeEvent event) -> {
                doLoadDados();
            });
        }
        return txtFilter;
    }

    protected Button getBtnAdd() {
        if (btnAdd == null) {
            btnAdd = new Button(FontAwesome.PLUS);
            btnAdd.addClickListener((final Button.ClickEvent event) -> {
                doAdd();
            });
        }
        return btnAdd;
    }

    protected Button getBtnEdit() {
        if (btnEdit == null) {
            btnEdit = new Button(FontAwesome.PENCIL);
            btnEdit.addClickListener((final Button.ClickEvent event) -> {
                doEdit();
            });
        }
        return btnEdit;
    }

    protected void doOnResize() {
        for (String propertyId : defaultCollapsible) {
            table.setColumnCollapsed(propertyId, Page.getCurrent()
                    .getBrowserWindowWidth() < 800);
        }
    }

    protected void doLoadDados() {
        doLoadDados(null);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.doLoadDados();
    }

    protected void doEdit() {
        if (getTable().getValue() != null) {
            T o = getContainer().getItem(getTable().getValue()).getBean();
            getBaseWindow().open(o.getId());
        } else {
            QGSUI.showWarn("Selecione o que deseja editar.");
        }
    }

    protected void doAdd() {
        getBaseWindow().open();
    }

    @Override
    public void onRefreshAction(BroadcastMessage message) {
        this.doLoadDados(message.getEmpresa());
    }

    @Override
    public void onResize(Page.BrowserWindowResizeEvent e) {
        doOnResize();
    }

    abstract protected Class<T> getClazz();

    abstract protected BaseWindow<ID, T> getBaseWindow();

    abstract protected String getColumnExpand();

    abstract protected String[] getColumns();

    abstract protected String[] getColumnsLabels();

    abstract protected Component[] getExtraToolbarComponents();

    abstract protected void doLoadDados(Empresa e);

}
