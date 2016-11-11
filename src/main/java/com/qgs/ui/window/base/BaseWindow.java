package com.qgs.ui.window.base;

import com.qgs.model.BaseBean;
import com.qgs.service.secutiry.SecurityUtils;
import com.qgs.ui.broadcast.BroadcastMessage;
import com.qgs.ui.broadcast.OriginalSender;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import javax.ejb.EJB;
import javax.inject.Inject;

/**
 *
 * @author rafael
 * @param <ID>
 * @param <T>
 */
public abstract class BaseWindow<ID, T extends BaseBean<ID>> extends Window {

    private TextField txtId;
    private Button btSave;
    private T dado;

    @EJB
    private SecurityUtils securityUtils;

    @Inject
    @OriginalSender
    private javax.enterprise.event.Event<BroadcastMessage> messageEvent;

    public BaseWindow(String styleWindow, String title) {
        super(title);

        addStyleName(styleWindow);
        Responsive.makeResponsive(this);

        center();

        addCloseShortcut(ShortcutAction.KeyCode.ESCAPE);
        setResizable(true);
        setClosable(true);
        setModal(true);
    }

    public void open(ID id) {
        open();
        this.doLoadDados(id);
    }

    public void open() {
        UI.getCurrent().addWindow(this);
        this.focus();
        this.doInitiate();
    }

    protected void sendRefresh() {
        messageEvent.fire(new BroadcastMessage(BroadcastMessage.Action.REFRESH, getSendRefreshClazz(), securityUtils.getEmpresa()));
    }

    protected TextField getTxtId() {
        if (txtId == null) {
            txtId = new TextField("Código");
            txtId.setEnabled(false);
            txtId.setWidth(100, Unit.PERCENTAGE);
        }
        return txtId;
    }

    protected Button getBtSave() {
        if (btSave == null) {
            btSave = new Button("Salvar");
            btSave.setIcon(FontAwesome.SAVE);
            btSave.addClickListener((Button.ClickEvent event) -> {
                doSave();

            });
        }
        return btSave;
    }

    public T getDado() {
        return dado;
    }

    public void setDado(T dado) {
        this.dado = dado;
    }

    abstract protected void doInitiate();

    abstract protected Class<?> getSendRefreshClazz();

    abstract protected void doLoadDados(ID id);

    abstract protected void doSave();

}
