package com.qgs.service;

import com.qgs.util.PersistenceUnitHelper;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author rafael
 * @param <T>
 */
@Stateless
public class ListAllService<T> {

    @Inject
    private PersistenceUnitHelper helper;

    public List<T> findAll(Class<T> clazz) {
        return helper.getEntityManager().createNamedQuery(clazz.getSimpleName() + ".findAll", clazz).getResultList();
    }

    public List<T> findAllByParam(Class<T> clazz, String param, Serializable value) {
        return helper.getEntityManager().createNamedQuery(clazz.getSimpleName() + ".findAllByParam", clazz).setParameter(param, value).getResultList();
    }

}
