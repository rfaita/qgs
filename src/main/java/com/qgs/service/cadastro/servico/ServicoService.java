package com.qgs.service.cadastro.servico;

import com.qgs.model.cadastro.servico.Servico;
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
public class ServicoService {

    @Inject
    private Validator validator;
    @Inject
    private PersistenceUnitHelper helper;
    @EJB
    private SecurityUtils securityUtils;

    private void validateSave(Servico o) throws ConstraintViolationException {
        Set<ConstraintViolation<Servico>> violations = validator.validate(o, Servico.SaveGroup.class);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<Servico>>(violations));
        }
    }

    public void save(Servico o) throws Exception {

        validateSave(o);

        if (o.getId() != null) {
            helper.getEntityManager().merge(o);
        } else {
            o.setEmpresa(securityUtils.getEmpresa());
            helper.getEntityManager().persist(o);
        }
    }

    public Servico findCompleteById(Long id) {
        Servico ret = findById(id);
        ret.getEpis().size();
        ret.getAtributos().size();
        ret.getAssociados().size();
        ret.getMateriais().size();
        ret.getCustos().size();
        ret.getTramites().size();
        return ret;
    }

    public Servico findById(Long id) {
        return helper.getEntityManager().find(Servico.class, id);
    }

    public List<Servico> findAllByServico(Servico o) {
        StringBuilder hql = new StringBuilder("SELECT o FROM Servico o ");
        hql.append("JOIN FETCH o.empresa ");
        hql.append("JOIN FETCH o.tipoServico ");
        hql.append("WHERE o.empresa.id = :idEmpresa ");

        if (o.getServico() != null && !o.getServico().isEmpty()) {
            hql.append("AND upper(o.material) LIKE :material ");
        }

        if (o.getTipoServico() != null) {
            hql.append("AND o.tipoServico.id = :idTipoServico ");
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

        if (o.getServico() != null && !o.getServico().isEmpty()) {
            q.setParameter("material", "%" + o.getServico().toUpperCase() + "%");
        }

        if (o.getTipoServico() != null) {
            q.setParameter("idTipoServico", o.getTipoServico().getId());
        }

        return q.getResultList();

    }

}
