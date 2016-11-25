package com.qgs.service;

import com.qgs.model.Usuario;
import com.qgs.util.PersistenceUnitHelper;
import com.qgs.util.security.SecurityRole;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;

/**
 *
 * @author rafael
 */
@Stateless
public class UsuarioService {

    @Inject
    private PersistenceUnitHelper helper;
    @Inject
    private Validator validator;


    public Usuario findUsuarioByUsuario(String usuario) {
        List<Usuario> ret = helper.getEntityManager()
                .createNamedQuery("Usuario.findByUsuario", Usuario.class)
                .setParameter("usuario", usuario.toUpperCase()).getResultList();

        if (ret.isEmpty()) {
            return null;
        } else {
            return ret.get(0);
        }

    }

    public Usuario findUsuarioByCPF(String cpf) {
        List<Usuario> ret = helper.getEntityManager()
                .createNamedQuery("Usuario.findByCPF", Usuario.class)
                .setParameter("cpf", cpf.toUpperCase()).getResultList();

        if (ret.isEmpty()) {
            return null;
        } else {
            return ret.get(0);
        }

    }

    public Usuario findUsuarioByCelular(String celular) {
        List<Usuario> ret = helper.getEntityManager()
                .createNamedQuery("Usuario.findByCelular", Usuario.class)
                .setParameter("celular", celular.toUpperCase()).getResultList();

        if (ret.isEmpty()) {
            return null;
        } else {
            return ret.get(0);
        }

    }

    private void validateSave(Usuario premio) throws ConstraintViolationException {
        Set<ConstraintViolation<Usuario>> violations = validator.validate(premio, Usuario.SaveGroup.class);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }
    }

    public Usuario criarUsuario(Usuario u) {
        validateSave(u);

        if (findUsuarioByUsuario(u.getUsuario()) != null) {
            throw new ValidationException("Usuário já cadastrado.");
        }

        if (findUsuarioByCPF(u.getCpf()) != null) {
            throw new ValidationException("CPF já cadastrado.");
        }

        if (findUsuarioByCelular(u.getCelular()) != null) {
            throw new ValidationException("Celular já cadastrado.");
        }

        if (!u.getSenha().equals(u.getSenhaConf())) {
            throw new ValidationException("Senha distintas.");
        }

        u.setRole(SecurityRole.QUEST.getRole());

        helper.getEntityManager().persist(u);

        return u;
    }

}
