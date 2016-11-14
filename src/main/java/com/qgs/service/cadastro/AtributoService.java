package com.qgs.service.cadastro;

import com.qgs.model.cadastro.Atributo;
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
public class AtributoService {

    @Inject
    private Validator validator;
    @Inject
    private PersistenceUnitHelper helper;
    @EJB
    private SecurityUtils securityUtils;

    private void validateSave(Atributo o) throws ConstraintViolationException {
        Set<ConstraintViolation<Atributo>> violations = validator.validate(o, Atributo.SaveGroup.class);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<Atributo>>(violations));
        }
    }

    public void save(Atributo o) throws Exception {

        validateSave(o);

        if (o.getId() != null) {
            helper.getEntityManager().merge(o);
        } else {
            o.setEmpresa(securityUtils.getEmpresa());
            helper.getEntityManager().persist(o);
        }
    }

    public Atributo findCompleteById(Integer id) {
        Atributo ret = findById(id);
        ret.getValoresAtributo().size();
        return ret;
    }
    public Atributo findById(Integer id) {
        return helper.getEntityManager().find(Atributo.class, id);
    }

    public void inactivate(Atributo o) throws Exception {

        Atributo in = findById(o.getId());
        in.setAtivo(Boolean.FALSE);

        helper.getEntityManager().merge(in);

    }

    public List<Atributo> findAllByAtributo(Atributo o) {
        StringBuilder hql = new StringBuilder("SELECT o FROM Atributo o ");
        hql.append("JOIN FETCH o.empresa ");
        hql.append("JOIN FETCH o.tipoAtributo ");
        hql.append("WHERE o.empresa.id = :idEmpresa ");

        if (o.getAtributo() != null && !o.getAtributo().isEmpty()) {
            hql.append("AND upper(o.atributo) LIKE :atributo ");
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

        if (o.getAtributo() != null && !o.getAtributo().isEmpty()) {
            q.setParameter("atributo", "%" + o.getAtributo().toUpperCase() + "%");
        }

        return q.getResultList();

    }

}
