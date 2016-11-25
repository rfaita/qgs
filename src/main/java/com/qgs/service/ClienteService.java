package com.qgs.service;

import com.qgs.model.Cliente;
import com.qgs.service.secutiry.SecurityUtils;
import com.qgs.util.PersistenceUnitHelper;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;

/**
 *
 * @author rafael
 */
@Stateless
public class ClienteService {

    @Inject
    private PersistenceUnitHelper helper;
    @Inject
    private Validator validator;
    @Inject
    private SecurityUtils securityUtils;

    public Cliente findClienteByCPF(String cpf) {
        List<Cliente> ret = helper.getEntityManager()
                .createNamedQuery("Cliente.findByCPF", Cliente.class)
                .setParameter("idEmpresa", securityUtils.getEmpresa().getId())
                .setParameter("cpf", cpf).getResultList();

        if (ret.isEmpty()) {
            return null;
        } else {
            return ret.get(0);
        }
    }

    public Cliente findClienteByCNPJ(String cnpj) {
        List<Cliente> ret = helper.getEntityManager()
                .createNamedQuery("Cliente.findByCNPJ", Cliente.class)
                .setParameter("idEmpresa", securityUtils.getEmpresa().getId())
                .setParameter("cnpj", cnpj).getResultList();

        if (ret.isEmpty()) {
            return null;
        } else {
            return ret.get(0);
        }
    }

    public Cliente findClienteByNome(String cliente) {
        List<Cliente> ret = helper.getEntityManager()
                .createNamedQuery("Cliente.findByNome", Cliente.class)
                .setParameter("idEmpresa", securityUtils.getEmpresa().getId())
                .setParameter("cliente", cliente.toUpperCase()).getResultList();

        if (ret.isEmpty()) {
            return null;
        } else {
            return ret.get(0);
        }
    }

    public Cliente findById(Long id) {
        List<Cliente> ret = helper.getEntityManager()
                .createNamedQuery("Cliente.findById", Cliente.class)
                .setParameter("idEmpresa", securityUtils.getEmpresa().getId())
                .setParameter("id", id).getResultList();

        if (ret.isEmpty()) {
            return null;
        } else {
            return ret.get(0);
        }
    }

}
