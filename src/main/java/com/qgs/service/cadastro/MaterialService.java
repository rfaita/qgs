package com.qgs.service.cadastro;

import com.qgs.model.cadastro.EPI;
import com.qgs.model.cadastro.Material;
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
public class MaterialService {

    @Inject
    private Validator validator;
    @Inject
    private PersistenceUnitHelper helper;
    @EJB
    private SecurityUtils securityUtils;

    private void validateSave(Material o) throws ConstraintViolationException {
        Set<ConstraintViolation<Material>> violations = validator.validate(o, Material.SaveGroup.class);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<Material>>(violations));
        }
    }

    public void save(Material o) throws Exception {

        validateSave(o);

        if (o.getId() != null) {
            helper.getEntityManager().merge(o);
        } else {
            o.setEmpresa(securityUtils.getEmpresa());
            helper.getEntityManager().persist(o);
        }
    }

    public Material findById(Integer id) {
        return helper.getEntityManager().find(Material.class, id);
    }

    public List<Material> findAllByMaterial(Material o) {
        StringBuilder hql = new StringBuilder("SELECT o FROM Material o ");
        hql.append("JOIN FETCH o.empresa ");
        hql.append("JOIN FETCH o.tipoMaterial ");
        hql.append("WHERE o.empresa.id = :idEmpresa ");

        if (o.getMaterial() != null && !o.getMaterial().isEmpty()) {
            hql.append("AND upper(o.material) LIKE :material ");
        }

        if (o.getTipoMaterial() != null) {
            hql.append("AND o.tipoMaterial.id = :idTipoMaterial ");
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

        if (o.getMaterial() != null && !o.getMaterial().isEmpty()) {
            q.setParameter("material", "%" + o.getMaterial().toUpperCase() + "%");
        }

        if (o.getTipoMaterial() != null) {
            q.setParameter("idTipoMaterial", o.getTipoMaterial().getId());
        }

        return q.getResultList();

    }

}
