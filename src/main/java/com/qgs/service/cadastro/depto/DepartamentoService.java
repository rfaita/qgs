package com.qgs.service.cadastro.depto;

import com.qgs.model.cadastro.depto.Departamento;
import com.qgs.service.secutiry.SecurityUtils;
import com.qgs.util.PersistenceUnitHelper;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

/**
 *
 * @author rafael
 */
@Stateless
public class DepartamentoService {

    @Inject
    private Validator validator;
    @Inject
    private PersistenceUnitHelper helper;
    @EJB
    private SecurityUtils securityUtils;

    private void validateSave(Departamento o) throws ConstraintViolationException {
        Set<ConstraintViolation<Departamento>> violations = validator.validate(o, Departamento.SaveGroup.class);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<Departamento>>(violations));
        }
    }

    public void save(Departamento epi) throws Exception {

        validateSave(epi);

        if (epi.getId() != null) {
            helper.getEntityManager().merge(epi);
        } else {
            epi.setEmpresa(securityUtils.getEmpresa());
            helper.getEntityManager().persist(epi);
        }
    }

    public Departamento findById(Integer id) {
        return helper.getEntityManager().find(Departamento.class, id);
    }

    public List<Departamento> findAllByDepartamento(Departamento o) {
        StringBuilder hql = new StringBuilder("SELECT o FROM Departamento o ");
        hql.append("JOIN FETCH o.tipoDepartamento ");
        hql.append("JOIN FETCH o.empresa ");
        hql.append("WHERE o.empresa.id = :idEmpresa ");

        if (o.getDepartamento() != null && !o.getDepartamento().isEmpty()) {
            hql.append("AND upper(o.epi) LIKE :tipoDepartamento ");
        }
        if (o.getTipoDepartamento() != null) {
            hql.append("AND o.tipoDepartamento.id = :idTipoDepartamento ");
        }

        hql.append("AND (coalesce(o.ativo, false) = true ");
        if (o.getAtivo() != null && !o.getAtivo()) {
            hql.append("OR coalesce(o.ativo, false) = false ");
        }
        hql.append(")");

        Query q = helper.getEntityManager().createQuery(hql.toString());

        if (o.getEmpresa() != null) {
            q.setParameter("idEmpresa", o.getEmpresa().getId());
        } else {
            q.setParameter("idEmpresa", securityUtils.getEmpresa().getId());
        }

        if (o.getTipoDepartamento() != null) {
            q.setParameter("idTipoDepartamento", o.getTipoDepartamento().getId());
        }

        if (o.getDepartamento() != null && !o.getDepartamento().isEmpty()) {
            q.setParameter("tipoDepartamento", "%" + o.getDepartamento().toUpperCase() + "%");
        }

        return q.getResultList();

    }

}
