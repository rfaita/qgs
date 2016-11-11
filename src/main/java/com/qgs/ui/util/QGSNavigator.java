package com.qgs.ui.util;

import com.qgs.service.secutiry.SecurityUtils;
import com.qgs.ui.view.DashboardView;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;

/**
 *
 * @author rafael
 */
public class QGSNavigator extends Navigator {

    public QGSNavigator(SecurityUtils securityUtils, CDIViewProvider cdiViewProvider, ComponentContainer componentContainer) {
        super(UI.getCurrent(), componentContainer);

        addProvider(cdiViewProvider);

        setErrorView(DashboardView.class);
    }

}
