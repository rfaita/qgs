package com.qgs.service.cadastro;

import com.qgs.model.cadastro.TipoMaterial;
import com.qgs.util.PersistenceUnitHelper;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author rafael
 */
@Stateless
public class TipoMaterialService {

    @Inject
    private PersistenceUnitHelper helper;

    public List<TipoMaterial> findAll() {
        return helper.getEntityManager().createNamedQuery("TipoMaterial.findAll", TipoMaterial.class).getResultList();
    }

}
