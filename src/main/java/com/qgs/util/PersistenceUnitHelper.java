package com.qgs.util;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class PersistenceUnitHelper {

    @PersistenceContext(name = "PA")
    private EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }
}
