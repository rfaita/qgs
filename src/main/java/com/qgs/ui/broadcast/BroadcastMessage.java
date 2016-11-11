package com.qgs.ui.broadcast;

import com.qgs.model.Empresa;
import java.io.Serializable;

/**
 *
 * @author rafael
 */
public class BroadcastMessage implements Serializable {

    private Action action;
    private Object sender;
    private Class view;
    private Empresa empresa;

    public BroadcastMessage(Action action, Class view, Empresa empresa) {
        this.action = action;
        this.view = view;
        this.empresa = empresa;
    }

    public BroadcastMessage() {
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Object getSender() {
        return sender;
    }

    public void setSender(Object sender) {
        this.sender = sender;
    }

    public Class getView() {
        return view;
    }

    public void setView(Class view) {
        this.view = view;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa provedor) {
        this.empresa = provedor;
    }

    public enum Action {
        REFRESH;
    }

}
