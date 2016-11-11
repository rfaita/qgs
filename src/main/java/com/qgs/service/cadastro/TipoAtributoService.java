package com.qgs.service.cadastro;

import com.qgs.model.cadastro.TipoAtributo;
import com.qgs.util.PersistenceUnitHelper;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author rafael
 */
@Stateless
public class TipoAtributoService {

    @Inject
    private PersistenceUnitHelper helper;

    public List<TipoAtributo> findAll() {
        return helper.getEntityManager().createNamedQuery("TipoAtributo.findAll", TipoAtributo.class).getResultList();
    }

}
