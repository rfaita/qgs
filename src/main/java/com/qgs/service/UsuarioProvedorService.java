package com.qgs.service;

import com.qgs.model.UsuarioProvedor;
import com.qgs.service.secutiry.SecurityUtils;
import com.qgs.util.PersistenceUnitHelper;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author rafael
 */
@Stateless
public class UsuarioProvedorService {

    @Inject
    private PersistenceUnitHelper helper;
    @EJB
    private SecurityUtils securityUtils;

    public UsuarioProvedor findUsuarioProvedorByUsuario(String usuario) {
        List<UsuarioProvedor> ret = helper.getEntityManager()
                .createNamedQuery("UsuarioProvedor.findByUsuario", UsuarioProvedor.class)
                .setParameter("usuario", usuario.toUpperCase()).getResultList();

        if (ret.isEmpty()) {
            return null;
        } else {
            return ret.get(0);
        }

    }

    public List<UsuarioProvedor> findUsuarioProvedorByProvedor() {
        return helper.getEntityManager()
                .createNamedQuery("UsuarioProvedor.findByProvedor", UsuarioProvedor.class)
                .setParameter("idProvedor", securityUtils.getEmpresa().getId()).getResultList();

    }

}
