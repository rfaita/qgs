package com.qgs.service.secutiry;

import com.qgs.util.security.SecurityRole;
import com.qgs.model.Empresa;
import com.qgs.util.security.QGSPrincipal;
import com.qgs.util.security.QGSEmpresaPrincipal;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.validation.ValidationException;

/**
 *
 * @author rafael
 */
@Stateless
public class SecurityUtils {

    @Resource
    private EJBContext context;

    public Boolean hasPermission(SecurityRole minRole) {
        return (minRole.getPriority() <= getRole().getPriority());
    }

    public QGSPrincipal getPrincipal() {
        if (context.getCallerPrincipal() instanceof QGSPrincipal) {
            return (QGSPrincipal) context.getCallerPrincipal();
        } else {
            throw new ValidationException("Usuário logado não é do tipo usuário comum.");
        }
    }

    public QGSEmpresaPrincipal getEmpresaPrincipal() {
        if (context.getCallerPrincipal() instanceof QGSEmpresaPrincipal) {
            return (QGSEmpresaPrincipal) context.getCallerPrincipal();
        } else {
            throw new ValidationException("Usuário logado não é do tipo provedor.");
        }
    }

    public Empresa getEmpresa() {
        return getEmpresaPrincipal().getUsuarioProvedor().getProvedor();
    }

    public SecurityRole getRole() {
        SecurityRole ret = SecurityRole.valueOf(getEmpresaPrincipal().getUsuarioProvedor().getRole().toUpperCase());
        if (ret != null) {
            return ret;
        } else {
            return SecurityRole.QUEST;

        }
    }

}
