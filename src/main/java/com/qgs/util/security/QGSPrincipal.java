package com.qgs.util.security;

import com.qgs.model.Usuario;
import java.security.Principal;

/**
 *
 * @author rafael
 */
public class QGSPrincipal implements Principal {

    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public QGSPrincipal(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String getName() {
        return this.getUsuario().getUsuario();
    }

}
