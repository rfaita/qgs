package com.qgs.rest.bo;

import com.qgs.model.Usuario;
import java.io.Serializable;

/**
 *
 * @author rafael
 */
public class UsuarioRetorno implements Serializable {

    private Usuario usuario;
    private String mensagemErro;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }

    public void setMensagemErro(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }

}
