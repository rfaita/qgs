package com.qgs.service.cadastro;

import com.qgs.model.cadastro.EPI;
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
public class EPIService {

    @Inject
    private Validator validator;
    @Inject
    private PersistenceUnitHelper helper;
    @EJB
    private SecurityUtils securityUtils;

    private void validateSave(EPI epi) throws ConstraintViolationException {
        Set<ConstraintViolation<EPI>> violations = validator.validate(epi, EPI.SaveGroup.class);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<EPI>>(violations));
        }
    }

    public void save(EPI epi) throws Exception {

        validateSave(epi);

        if (epi.getId() != null) {
            helper.getEntityManager().merge(epi);
        } else {
            epi.setEmpresa(securityUtils.getEmpresa());
            helper.getEntityManager().persist(epi);
        }
    }

    public EPI findById(Integer id) {
        return helper.getEntityManager().find(EPI.class, id);
    }

    public void inactivate(EPI epi) throws Exception {

        EPI in = findById(epi.getId());
        in.setAtivo(Boolean.FALSE);

        helper.getEntityManager().merge(in);

    }

    public List<EPI> findAllByEPI(EPI o) {
        StringBuilder hql = new StringBuilder("SELECT o FROM EPI o ");
        hql.append("JOIN FETCH o.empresa ");
        hql.append("WHERE o.empresa.id = :idEmpresa ");

        if (o.getEpi() != null && !o.getEpi().isEmpty()) {
            hql.append("AND upper(o.epi) LIKE :epi ");
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

        if (o.getEpi() != null && !o.getEpi().isEmpty()) {
            q.setParameter("epi", "%" + o.getEpi().toUpperCase() + "%");
        }

        return q.getResultList();

    }

}
