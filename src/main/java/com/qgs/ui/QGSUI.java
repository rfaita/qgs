package com.qgs.ui;

import com.qgs.ui.broadcast.CDIBroadcaster;
import com.qgs.model.Empresa;
import com.qgs.service.UsuarioProvedorService;
import com.qgs.service.secutiry.SecurityUtils;
import com.qgs.ui.broadcast.BroadcastMessage;
import com.qgs.ui.view.LoginView;
import com.qgs.ui.view.MainView;
import com.qgs.ui.view.RefreshView;
import com.qgs.ui.view.ResizeView;
import com.qgs.util.security.QGSEmpresaPrincipal;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.cdi.access.JaasAccessControl;
import com.vaadin.server.*;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ui.Transport;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import java.security.Principal;
import java.util.Locale;
import javax.ejb.EJB;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

@CDIUI("")
@Theme("dashboard")
@Push(transport = Transport.WEBSOCKET_XHR)
public final class QGSUI extends UI {

    @EJB
    private SecurityUtils securityUtils;
    @EJB
    private UsuarioProvedorService usuarioProvedorService;
    @Inject
    private CDIViewProvider cdiViewProvider;
    @Inject
    private CDIBroadcaster cdiBroadcaster;

    private Empresa provedorLogado;

    @Override
    protected void init(final VaadinRequest request) {

        setLocale(new Locale("pt", "BR"));

        Responsive.makeResponsive(this);

        cdiBroadcaster.register(this);

        Principal p = JaasAccessControl.getCurrentRequest().getUserPrincipal();

        if (p == null) {

            Page.getCurrent().setTitle("Quality GS - Login");
            setContent(new LoginView());

        } else if (p instanceof QGSEmpresaPrincipal) {

            this.provedorLogado = ((QGSEmpresaPrincipal) JaasAccessControl.getCurrentRequest().getUserPrincipal()).getUsuarioProvedor().getProvedor();

            Page.getCurrent().setTitle("Quality GS - Gerêncial");

            setContent(new MainView(securityUtils, cdiViewProvider));

            navegateToDefaultView();

            Page.getCurrent().addBrowserWindowResizeListener((final BrowserWindowResizeEvent event) -> {
                if (getNavigator().getCurrentView() instanceof ResizeView) {
                    ((ResizeView) getNavigator().getCurrentView()).onResize(event);
                }
            });

            addStyleName(ValoTheme.UI_WITH_MENU);

        } else {
            Page.getCurrent().setTitle("Quality GS - Acesso restrito");
        }

    }

    private void navegateToDefaultView() {
        if (Page.getCurrent().getUriFragment() == null || Page.getCurrent().getUriFragment().isEmpty()) {
            getNavigator().navigateTo("dashboard");
        }
    }

    public void observeMessage(@Observes BroadcastMessage event) {
        if (event.getAction().equals(BroadcastMessage.Action.REFRESH)) {
            if (event.getView().equals(getNavigator().getCurrentView().getClass())) {
                ((RefreshView) getNavigator().getCurrentView()).onRefreshAction(event);
            }
        }
    }

    @Override
    public void detach() {
        super.detach();
        cdiBroadcaster.unregister(this);
    }

    public static void showInfo(String m) {
        Notification n = new Notification(m, Notification.Type.HUMANIZED_MESSAGE);
        n.setPosition(Position.TOP_RIGHT);
        n.setDelayMsec(3000);
        n.show(Page.getCurrent());
    }

    public static void showWarn(String m) {
        Notification n = new Notification(m, Notification.Type.TRAY_NOTIFICATION);
        n.setPosition(Position.TOP_RIGHT);
        n.setDelayMsec(4000);
        n.show(Page.getCurrent());
    }

    public static void showError(String m) {
        Notification n = new Notification(m, Notification.Type.ERROR_MESSAGE);
        n.setPosition(Position.TOP_RIGHT);
        n.setDelayMsec(4000);
        n.show(Page.getCurrent());
    }

    public static void doCatch(Exception ex) {

        if (ex.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException e = (ConstraintViolationException) ex.getCause();
            StringBuilder message = new StringBuilder();
            e.getConstraintViolations().stream().forEach((c) -> {
                message.append(c.getMessage()).append("\n");
            });
            QGSUI.showWarn(message.toString());
        } else if (ex.getCause() instanceof ValidationException) {
            QGSUI.showWarn(ex.getCause().getMessage());
        } else {
            QGSUI.showError(ex.getMessage());
        }
    }

    public Empresa getProvedorLogado() {
        return provedorLogado;
    }

    public SecurityUtils getSecurityUtils() {
        return securityUtils;
    }

    public UsuarioProvedorService getUsuarioProvedorService() {
        return usuarioProvedorService;
    }

}
