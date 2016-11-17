package com.qgs.service.cadastro.equipe;

import com.qgs.model.cadastro.equipe.Equipe;
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
public class EquipeService {

    @Inject
    private Validator validator;
    @Inject
    private PersistenceUnitHelper helper;
    @EJB
    private SecurityUtils securityUtils;

    private void validateSave(Equipe o) throws ConstraintViolationException {
        Set<ConstraintViolation<Equipe>> violations = validator.validate(o, Equipe.SaveGroup.class);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<Equipe>>(violations));
        }
    }

    public void save(Equipe o) throws Exception {

        validateSave(o);

        if (o.getId() != null) {
            helper.getEntityManager().merge(o);
        } else {
            o.setEmpresa(securityUtils.getEmpresa());
            helper.getEntityManager().persist(o);
        }
    }

    public Equipe findById(Long id) {
        return helper.getEntityManager().find(Equipe.class, id);
    }

    public List<Equipe> findAllByEquipe(Equipe o) {
        StringBuilder hql = new StringBuilder("SELECT o FROM Equipe o ");
        hql.append("JOIN FETCH o.empresa ");
        hql.append("JOIN FETCH o.tipoEquipe ");
        hql.append("WHERE o.empresa.id = :idEmpresa ");

        if (o.getEquipe() != null && !o.getEquipe().isEmpty()) {
            hql.append("AND upper(o.material) LIKE :material ");
        }

        if (o.getTipoEquipe() != null) {
            hql.append("AND o.tipoEquipe.id = :idTipoEquipe ");
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

        if (o.getEquipe() != null && !o.getEquipe().isEmpty()) {
            q.setParameter("material", "%" + o.getEquipe().toUpperCase() + "%");
        }

        if (o.getTipoEquipe() != null) {
            q.setParameter("idTipoEquipe", o.getTipoEquipe().getId());
        }

        return q.getResultList();

    }

}
