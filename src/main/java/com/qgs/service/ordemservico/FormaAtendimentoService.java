package com.qgs.service.ordemservico;

import com.qgs.model.ordemservico.FormaAtendimento;
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
public class FormaAtendimentoService {

    @Inject
    private Validator validator;
    @Inject
    private PersistenceUnitHelper helper;
    @EJB
    private SecurityUtils securityUtils;

    private void validateSave(FormaAtendimento epi) throws ConstraintViolationException {
        Set<ConstraintViolation<FormaAtendimento>> violations = validator.validate(epi, FormaAtendimento.SaveGroup.class);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<FormaAtendimento>>(violations));
        }
    }

    public void save(FormaAtendimento epi) throws Exception {

        validateSave(epi);

        if (epi.getId() != null) {
            helper.getEntityManager().merge(epi);
        } else {
            epi.setEmpresa(securityUtils.getEmpresa());
            helper.getEntityManager().persist(epi);
        }
    }

    public FormaAtendimento findById(Integer id) {
        return helper.getEntityManager().find(FormaAtendimento.class, id);
    }

    public List<FormaAtendimento> findAllByFormaAtendimento(FormaAtendimento o) {
        StringBuilder hql = new StringBuilder("SELECT o FROM FormaAtendimento o ");
        hql.append("JOIN FETCH o.empresa ");
        hql.append("WHERE o.empresa.id = :idEmpresa ");

        if (o.getFormaAtendimento() != null && !o.getFormaAtendimento().isEmpty()) {
            hql.append("AND upper(o.formaAtendimento) LIKE :formaAtendimento ");
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

        if (o.getFormaAtendimento() != null && !o.getFormaAtendimento().isEmpty()) {
            q.setParameter("formaAtendimento", "%" + o.getFormaAtendimento().toUpperCase() + "%");
        }

        return q.getResultList();

    }

}
