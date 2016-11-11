package com.qgs.rest;

import com.qgs.model.Usuario;
import com.qgs.rest.bo.UsuarioRetorno;
import com.qgs.service.UsuarioService;
import javax.inject.Inject;
import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author rafael
 */
@Path("/usuario")
public class UsuarioResource {

    @Inject
    private UsuarioService usuarioService;

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public UsuarioRetorno criarUsuario(Usuario u) {
        UsuarioRetorno ret = new UsuarioRetorno();
        try {
            ret.setUsuario(usuarioService.criarUsuario(u));

        } catch (Exception ex) {
            if (ex.getCause() instanceof ValidationException) {
                ret.setMensagemErro(ex.getCause().getMessage());
            } else {
                ret.setMensagemErro(ex.getMessage());
            }
        }
        return ret;
    }
}
