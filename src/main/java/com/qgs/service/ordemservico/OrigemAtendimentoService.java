package com.qgs.service.ordemservico;

import com.qgs.model.ordemservico.OrigemAtendimento;
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
public class OrigemAtendimentoService {

    @Inject
    private Validator validator;
    @Inject
    private PersistenceUnitHelper helper;
    @EJB
    private SecurityUtils securityUtils;

    private void validateSave(OrigemAtendimento epi) throws ConstraintViolationException {
        Set<ConstraintViolation<OrigemAtendimento>> violations = validator.validate(epi, OrigemAtendimento.SaveGroup.class);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<OrigemAtendimento>>(violations));
        }
    }

    public void save(OrigemAtendimento epi) throws Exception {

        validateSave(epi);

        if (epi.getId() != null) {
            helper.getEntityManager().merge(epi);
        } else {
            epi.setEmpresa(securityUtils.getEmpresa());
            helper.getEntityManager().persist(epi);
        }
    }

    public OrigemAtendimento findById(Integer id) {
        return helper.getEntityManager().find(OrigemAtendimento.class, id);
    }

    public List<OrigemAtendimento> findAllByOrigemAtendimento(OrigemAtendimento o) {
        StringBuilder hql = new StringBuilder("SELECT o FROM OrigemAtendimento o ");
        hql.append("JOIN FETCH o.empresa ");
        hql.append("WHERE o.empresa.id = :idEmpresa ");

        if (o.getOrigemAtendimento() != null && !o.getOrigemAtendimento().isEmpty()) {
            hql.append("AND upper(o.origemAtendimento) LIKE :origemAtendimento ");
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

        if (o.getOrigemAtendimento() != null && !o.getOrigemAtendimento().isEmpty()) {
            q.setParameter("origemAtendimento", "%" + o.getOrigemAtendimento().toUpperCase() + "%");
        }

        return q.getResultList();

    }

}
