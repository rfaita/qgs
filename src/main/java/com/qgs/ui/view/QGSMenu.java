package com.qgs.ui.view;

import com.qgs.model.UsuarioProvedor;
import com.qgs.ui.QGSUI;
import com.qgs.service.secutiry.SecurityUtils;
import com.qgs.ui.view.cadastro.AtributoView;
import com.qgs.ui.view.cadastro.EPIView;
import com.qgs.ui.view.cadastro.MaterialView;
import com.qgs.ui.view.cadastro.depto.DepartamentoView;
import com.qgs.ui.view.cadastro.depto.TipoDepartamentoView;
import com.qgs.ui.view.cadastro.equipe.EquipeView;
import com.qgs.ui.view.cadastro.rubrica.RubricaView;
import com.qgs.ui.view.cadastro.servico.ServicoView;
import com.qgs.ui.view.setor.SetorView;
import com.qgs.ui.window.UsuarioProvedorWindow;
import com.qgs.util.security.QGSEmpresaPrincipal;
import com.qgs.util.security.SecurityRole;
import com.vaadin.cdi.access.JaasAccessControl;
import com.vaadin.server.ClassResource;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author rafael
 */
public class QGSMenu extends CustomComponent {

    private Map<String, ItemMenu> items;

    private CssLayout content;
    private CssLayout contentItems;
    private HorizontalLayout hlMenuTop;
    private MenuBar mbSettings;
    private Button btShowMenu;

    private SecurityUtils securityUtils;

    public QGSMenu(SecurityUtils securityUtils) {

        this.securityUtils = securityUtils;

        setPrimaryStyleName(ValoTheme.MENU_ROOT);
        setId("qgs-menu");
        setSizeUndefined();

        setCompositionRoot(getContent());
    }

    private HorizontalLayout getHlMenuTop() {
        if (hlMenuTop == null) {
            hlMenuTop = new HorizontalLayout();

            final Label title = new Label("Quality GS <strong>Gerencial</strong>", ContentMode.HTML);
            title.setSizeUndefined();

            hlMenuTop.addComponent(title);
            hlMenuTop.setComponentAlignment(title, Alignment.MIDDLE_CENTER);
            hlMenuTop.addStyleName(ValoTheme.MENU_TITLE);
        }

        return hlMenuTop;
    }

    private Button getBtShowMenu() {
        if (btShowMenu == null) {
            btShowMenu = new Button("Opções", (final Button.ClickEvent event) -> {
                if (getContent().getStyleName().contains("valo-menu-visible")) {
                    getContent().removeStyleName("valo-menu-visible");
                } else {
                    getContent().addStyleName("valo-menu-visible");
                }
            });
            btShowMenu.setIcon(FontAwesome.LIST);
            btShowMenu.addStyleName("valo-menu-toggle");
            btShowMenu.addStyleName(ValoTheme.BUTTON_BORDERLESS);
            btShowMenu.addStyleName(ValoTheme.BUTTON_SMALL);
        }
        return btShowMenu;
    }

    public void hide() {
        getContent().removeStyleName("valo-menu-visible");
    }

    private MenuBar getMbSettings() {
        if (mbSettings == null) {
            try {
                mbSettings = new MenuBar();
                mbSettings.addStyleName("user-menu");

                QGSEmpresaPrincipal principal = securityUtils.getEmpresaPrincipal();

                UsuarioProvedor up = principal.getUsuarioProvedor();

                final MenuBar.MenuItem settingsItem = mbSettings.addItem(up.getNome(), (up.getAvatar() == null ? new ClassResource("nobody-300px.jpg") : new ExternalResource(up.getAvatar())), null);
                settingsItem.addItem("Alterar perfil", new MenuBar.Command() {
                    @Override
                    public void menuSelected(MenuBar.MenuItem selectedItem) {
                        UsuarioProvedorWindow.open((QGSUI) UI.getCurrent());
                    }
                });
                settingsItem.addItem("Preferencias", null);
                settingsItem.addSeparator();
                settingsItem.addItem("Sair", (MenuBar.MenuItem selectedItem) -> {
                    try {
                        JaasAccessControl.logout();
                        Page.getCurrent().reload();
                    } catch (Exception ex) {
                        QGSUI.showError(ex.getMessage());
                    }
                });
            } catch (Exception ex) {
                QGSUI.showError(ex.getMessage());
            }
        }
        return mbSettings;
    }

    public Map<String, ItemMenu> getItems() {
        if (items == null) {
            items = new LinkedHashMap<String, ItemMenu>();

            items.put(DashboardView.VIEW_ID, new ItemMenu("Dashboard", FontAwesome.DASHBOARD, SecurityRole.USER));
            items.put(EPIView.VIEW_ID, new ItemMenu("EPI", FontAwesome.DIAMOND, SecurityRole.USER));
            items.put(MaterialView.VIEW_ID, new ItemMenu("Material", FontAwesome.DIAMOND, SecurityRole.USER));
            items.put(AtributoView.VIEW_ID, new ItemMenu("Atributo", FontAwesome.DIAMOND, SecurityRole.USER));
            items.put(SetorView.VIEW_ID, new ItemMenu("Setor", FontAwesome.DIAMOND, SecurityRole.USER));
            items.put(TipoDepartamentoView.VIEW_ID, new ItemMenu("Tipo departamento", FontAwesome.DIAMOND, SecurityRole.USER));
            items.put(DepartamentoView.VIEW_ID, new ItemMenu("Departamento", FontAwesome.DIAMOND, SecurityRole.USER));
            items.put(RubricaView.VIEW_ID, new ItemMenu("Rubrica", FontAwesome.DIAMOND, SecurityRole.USER));
            items.put(ServicoView.VIEW_ID, new ItemMenu("Serviços", FontAwesome.OPENCART, SecurityRole.USER));
            items.put(EquipeView.VIEW_ID, new ItemMenu("Equipes", FontAwesome.OPENCART, SecurityRole.USER));
        }
        return items;
    }

    public CssLayout getContentItems() {
        if (contentItems == null) {
            contentItems = new CssLayout();
            contentItems.setPrimaryStyleName("valo-menuitems");

            try {
                for (final Map.Entry<String, ItemMenu> item : getItems().entrySet()) {
                    if (securityUtils.hasPermission(item.getValue().getMinRole())) {
                        final Button b = new Button(item.getValue().getMenu(), item.getValue().getIcon());
                        b.addClickListener((Button.ClickEvent event) -> {
                            UI.getCurrent().getNavigator().navigateTo(item.getKey());
                        });
                        b.setHtmlContentAllowed(true);
                        b.setPrimaryStyleName(ValoTheme.MENU_ITEM);
                        contentItems.addComponent(b);
                    }
                }
            } catch (Exception ex) {
                QGSUI.showError(ex.getMessage());
            }

        }
        return contentItems;
    }

    private CssLayout getContent() {
        if (content == null) {
            content = new CssLayout();

            content.addStyleName("sidebar");
            content.addStyleName(ValoTheme.MENU_PART);
            content.addStyleName("no-vertical-drag-hints");
            content.addStyleName("no-horizontal-drag-hints");
            content.setWidth(null);
            content.setHeight("100%");

            content.addComponent(getHlMenuTop());

            content.addComponent(getBtShowMenu());

            content.addComponent(getMbSettings());

            content.addComponent(getContentItems());
        }

        return content;
    }

    public class ItemMenu {

        private String menu;
        private Resource icon;
        private SecurityRole minRole;

        public SecurityRole getMinRole() {
            return minRole;
        }

        public void setMinRole(SecurityRole minRole) {
            this.minRole = minRole;
        }

        public String getMenu() {
            return menu;
        }

        public void setMenu(String menu) {
            this.menu = menu;
        }

        public Resource getIcon() {
            return icon;
        }

        public void setIcon(Resource icon) {
            this.icon = icon;
        }

        public ItemMenu(String menu, Resource icon, SecurityRole minRole) {
            this.menu = menu;
            this.icon = icon;
            this.minRole = minRole;
        }

    }

}
