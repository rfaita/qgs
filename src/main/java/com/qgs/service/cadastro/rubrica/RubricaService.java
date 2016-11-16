package com.qgs.service.cadastro.rubrica;

import com.qgs.model.cadastro.rubrica.Rubrica;
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
public class RubricaService {

    @Inject
    private Validator validator;
    @Inject
    private PersistenceUnitHelper helper;
    @EJB
    private SecurityUtils securityUtils;

    private void validateSave(Rubrica o) throws ConstraintViolationException {
        Set<ConstraintViolation<Rubrica>> violations = validator.validate(o, Rubrica.SaveGroup.class);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<Rubrica>>(violations));
        }
    }

    public void save(Rubrica o) throws Exception {

        validateSave(o);

        if (o.getId() != null) {
            helper.getEntityManager().merge(o);
        } else {
            o.setEmpresa(securityUtils.getEmpresa());
            helper.getEntityManager().persist(o);
        }
    }

    public Rubrica findById(Integer id) {
        return helper.getEntityManager().find(Rubrica.class, id);
    }

    public List<Rubrica> findAllByRubrica(Rubrica o) {
        StringBuilder hql = new StringBuilder("SELECT o FROM Rubrica o ");
        hql.append("JOIN FETCH o.empresa ");
        hql.append("JOIN FETCH o.tipoRubrica ");
        hql.append("WHERE o.empresa.id = :idEmpresa ");

        if (o.getRubrica() != null && !o.getRubrica().isEmpty()) {
            hql.append("AND upper(o.material) LIKE :material ");
        }

        if (o.getTipoRubrica() != null) {
            hql.append("AND o.tipoRubrica.id = :idTipoRubrica ");
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

        if (o.getRubrica() != null && !o.getRubrica().isEmpty()) {
            q.setParameter("material", "%" + o.getRubrica().toUpperCase() + "%");
        }

        if (o.getTipoRubrica() != null) {
            q.setParameter("idTipoRubrica", o.getTipoRubrica().getId());
        }

        return q.getResultList();

    }

}
