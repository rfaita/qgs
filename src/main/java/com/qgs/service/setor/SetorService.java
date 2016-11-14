package com.qgs.service.setor;

import com.qgs.model.cadastro.Atributo;
import com.qgs.model.cadastro.setor.Setor;
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
public class SetorService {

    @Inject
    private Validator validator;
    @Inject
    private PersistenceUnitHelper helper;
    @EJB
    private SecurityUtils securityUtils;

    private void validateSave(Setor o) throws ConstraintViolationException {
        Set<ConstraintViolation<Setor>> violations = validator.validate(o, Setor.SaveGroup.class);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<Setor>>(violations));
        }
    }

    public void save(Setor o) throws Exception {

        validateSave(o);

        if (o.getId() != null) {
            helper.getEntityManager().merge(o);
        } else {
            o.setEmpresa(securityUtils.getEmpresa());
            helper.getEntityManager().persist(o);
        }
    }

    public Setor findCompleteById(Integer id) {
        Setor ret = findById(id);
        ret.getCriterios().size();
        return ret;
    }

    public Setor findById(Integer id) {
        return helper.getEntityManager().find(Setor.class, id);
    }

    public List<Setor> findAllBySetor(Setor o) {
        StringBuilder hql = new StringBuilder("SELECT o FROM Setor o ");
        hql.append("JOIN FETCH o.empresa ");
        hql.append("WHERE o.empresa.id = :idEmpresa ");

        if (o.getSetor() != null && !o.getSetor().isEmpty()) {
            hql.append("AND upper(o.setor) LIKE :setor ");
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

        if (o.getSetor() != null && !o.getSetor().isEmpty()) {
            q.setParameter("setor", "%" + o.getSetor().toUpperCase() + "%");
        }

        return q.getResultList();

    }

}
