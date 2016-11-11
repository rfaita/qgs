package com.qgs.ui.view;

import com.qgs.ui.QGSUI;
import com.qgs.ui.util.QGSNavigator;
import com.qgs.service.secutiry.SecurityUtils;
import com.qgs.ui.view.QGSMenu.ItemMenu;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author rafael
 */
public class MainView extends HorizontalLayout {

    private QGSNavigator navigator;
    private QGSMenu menu;
    private ComponentContainer content;

    private SecurityUtils securityUtils;
    private CDIViewProvider cdiViewProvider;

    public MainView(SecurityUtils securityUtils, CDIViewProvider cdiViewProvider) {

        this.securityUtils = securityUtils;
        this.cdiViewProvider = cdiViewProvider;

        setSizeFull();
        addStyleName("mainview");

        addComponent(getMenu());

        addComponent(getContent());
        setExpandRatio(getContent(), 1.0f);

        getNavigator();

        if (Page.getCurrent().getWebBrowser().isIE()
                && Page.getCurrent().getWebBrowser().getBrowserMajorVersion() == 9) {
            getMenu().setWidth("320px");
        }

    }

    private QGSMenu getMenu() {
        if (menu == null) {
            menu = new QGSMenu(securityUtils);
        }
        return menu;
    }

    private ComponentContainer getContent() {
        if (content == null) {
            content = new CssLayout();
            content.addStyleName("view-content");
            content.setSizeFull();
        }
        return content;
    }

    private QGSNavigator getNavigator() {
        if (navigator == null) {
            navigator = new QGSNavigator(securityUtils, cdiViewProvider, getContent());

            navigator.addViewChangeListener(new ViewChangeListener() {
                @Override
                public boolean beforeViewChange(ViewChangeListener.ViewChangeEvent event) {
                    try {
                        return securityUtils.hasPermission(getMenu().getItems().get(event.getViewName()).getMinRole());
                    } catch (Exception ex) {
                        QGSUI.showError(ex.getMessage());
                    }
                    return false;
                }

                @Override
                public void afterViewChange(ViewChangeListener.ViewChangeEvent event) {
                    for (final Iterator<Component> it = getMenu().getContentItems().iterator(); it.hasNext();) {
                        it.next().removeStyleName("selected");
                    }
                    for (final Map.Entry<String, ItemMenu> item : getMenu().getItems().entrySet()) {
                        if (event.getViewName().equals(item.getKey())) {
                            for (final Iterator<Component> it = getMenu().getContentItems().iterator(); it.hasNext();) {
                                final Component c = it.next();
                                if (c.getCaption() != null && c.getCaption().startsWith(item.getValue().getMenu())) {
                                    c.addStyleName("selected");
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    getMenu().hide();
                }
            });

        }
        return navigator;
    }

}
