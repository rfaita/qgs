package com.qgs.service.cadastro.depto;

import com.qgs.model.cadastro.depto.TipoDepartamento;
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
public class TipoDepartamentoService {

    @Inject
    private Validator validator;
    @Inject
    private PersistenceUnitHelper helper;
    @EJB
    private SecurityUtils securityUtils;

    private void validateSave(TipoDepartamento epi) throws ConstraintViolationException {
        Set<ConstraintViolation<TipoDepartamento>> violations = validator.validate(epi, TipoDepartamento.SaveGroup.class);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<TipoDepartamento>>(violations));
        }
    }

    public void save(TipoDepartamento epi) throws Exception {

        validateSave(epi);

        if (epi.getId() != null) {
            helper.getEntityManager().merge(epi);
        } else {
            epi.setEmpresa(securityUtils.getEmpresa());
            helper.getEntityManager().persist(epi);
        }
    }

    public TipoDepartamento findById(Integer id) {
        return helper.getEntityManager().find(TipoDepartamento.class, id);
    }

    public List<TipoDepartamento> findAllByTipoDepartamento(TipoDepartamento o) {
        StringBuilder hql = new StringBuilder("SELECT o FROM TipoDepartamento o ");
        hql.append("JOIN FETCH o.tipoServicoAtendido ");
        hql.append("JOIN FETCH o.empresa ");
        hql.append("WHERE o.empresa.id = :idEmpresa ");

        if (o.getTipoDepartamento() != null && !o.getTipoDepartamento().isEmpty()) {
            hql.append("AND upper(o.tipoDepartamento) LIKE :tipoDepartamento ");
        }
        if (o.getTipoServicoAtendido() != null) {
            hql.append("AND o.tipoServicoAtendido.id = :idTipoServicoAtendido ");
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

        if (o.getTipoServicoAtendido() != null) {
            q.setParameter("idTipoServicoAtendido", o.getTipoServicoAtendido().getId());
        }

        if (o.getTipoDepartamento() != null && !o.getTipoDepartamento().isEmpty()) {
            q.setParameter("tipoDepartamento", "%" + o.getTipoDepartamento().toUpperCase() + "%");
        }

        return q.getResultList();

    }

}
