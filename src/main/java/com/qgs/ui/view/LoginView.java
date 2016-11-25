package com.qgs.ui.view;

import com.qgs.ui.QGSUI;
import com.qgs.ui.util.BrowserCookie;
import com.vaadin.cdi.access.JaasAccessControl;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.security.auth.login.LoginException;

/**
 *
 * @author rafael
 */
public class LoginView extends VerticalLayout {

    private TextField txtUsuario;
    private PasswordField txtPassword;
    private Button btSignin;
    private CheckBox ckRemenber;

    public LoginView() {

        Component loginForm = getLoginForm();
        this.setSizeFull();
        this.addComponent(loginForm);
        this.setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
    }

    private Component getLoginForm() {
        final VerticalLayout loginPanel = new VerticalLayout();
        loginPanel.setSizeUndefined();
        loginPanel.setSpacing(true);
        Responsive.makeResponsive(loginPanel);
        loginPanel.addStyleName("login-panel");

        loginPanel.addComponent(getLabels());
        loginPanel.addComponent(getFields());
        loginPanel.addComponent(getCkRemenber());
        return loginPanel;
    }

    private CheckBox getCkRemenber() {
        if (ckRemenber == null) {
            ckRemenber = new CheckBox("Lembrar-me", true);
        }
        return ckRemenber;
    }

    private TextField getTxtUsuario() {
        if (txtUsuario == null) {
            txtUsuario = new TextField("Usuário");
            txtUsuario.setId("usuario");
            txtUsuario.setIcon(FontAwesome.USER);
            txtUsuario.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
            txtUsuario.focus();
        }
        return txtUsuario;
    }

    private PasswordField getTxtPassword() {
        if (txtPassword == null) {
            txtPassword = new PasswordField("Senha");
            txtPassword.setId("senha");
            txtPassword.setIcon(FontAwesome.LOCK);
            txtPassword.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        }
        return txtPassword;
    }

    private Button getBtSignin() {
        if (btSignin == null) {
            btSignin = new Button("Entrar");
            btSignin.setId("entrar");
            btSignin.addStyleName(ValoTheme.BUTTON_PRIMARY);
            btSignin.setClickShortcut(ShortcutAction.KeyCode.ENTER);
            btSignin.addClickListener((final Button.ClickEvent event) -> {
                login();
            });
        }
        return btSignin;
    }

    private Component getFields() {
        HorizontalLayout fields = new HorizontalLayout();
        fields.setSpacing(true);
        fields.addStyleName("fields");

        fields.addComponents(getTxtUsuario(), getTxtPassword(), getBtSignin());
        fields.setComponentAlignment(getBtSignin(), Alignment.BOTTOM_LEFT);

        return fields;
    }

    private Component getLabels() {
        CssLayout labels = new CssLayout();
        labels.addStyleName("labels");

        Label welcome = new Label("Bem vindo");
        welcome.setSizeUndefined();
        welcome.addStyleName(ValoTheme.LABEL_H4);
        welcome.addStyleName(ValoTheme.LABEL_COLORED);
        labels.addComponent(welcome);

        Label title = new Label("Quality GS - Gerêncial");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H3);
        title.addStyleName(ValoTheme.LABEL_LIGHT);
        labels.addComponent(title);
        return labels;
    }

    private void login() {
        try {

            if (getTxtUsuario().getValue() == null || getTxtUsuario().getValue().isEmpty()) {
                QGSUI.showInfo("Forneça o usuário.");
                return;
            }
            if (getTxtPassword().getValue() == null || getTxtPassword().getValue().isEmpty()) {
                QGSUI.showInfo("Forneça a senha. ");
                return;
            }

            Date expires = Date.from(Instant.now().plus(3000, ChronoUnit.DAYS));
            BrowserCookie.setCookie("remenberme", String.valueOf(getCkRemenber().getValue()), expires);
            if (getCkRemenber().getValue()) {
                BrowserCookie.setCookie("usuario", getTxtUsuario().getValue(), expires);
            } else {
                BrowserCookie.setCookie("usuario", null);
            }

            JaasAccessControl.login(getTxtUsuario().getValue(), getTxtPassword().getValue());
            Page.getCurrent().reload();

        } catch (Exception ex) {
            if (ex.getCause() instanceof LoginException) {
                QGSUI.showWarn(ex.getMessage());
            } else {
                QGSUI.showWarn("Usuário e/ou senha inválidos.");
            }
        }
    }

}
