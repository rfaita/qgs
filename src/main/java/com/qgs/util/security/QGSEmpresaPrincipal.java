package com.qgs.util.security;

import com.qgs.model.UsuarioProvedor;
import java.security.Principal;

/**
 *
 * @author rafael
 */
public class QGSEmpresaPrincipal implements Principal {

    private UsuarioProvedor usuarioProvedor;

    public UsuarioProvedor getUsuarioProvedor() {
        return usuarioProvedor;
    }

    public void setUsuarioProvedor(UsuarioProvedor usuarioProvedor) {
        this.usuarioProvedor = usuarioProvedor;
    }

    public QGSEmpresaPrincipal(UsuarioProvedor usuarioProvedor) {
        this.usuarioProvedor = usuarioProvedor;
    }

    @Override
    public String getName() {
        return this.getUsuarioProvedor().getUsuario();
    }

}
