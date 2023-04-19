package org.backender.hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class PersistenceService<T> {
    Class<T> classType;
    private EntityManager em = getEntityManager();

    public PersistenceService(Class<T> classType) {
        this.classType = classType;
    }

    public void savePage(T object) {
        em.getTransaction().begin();
        em.persist(object);
        em.getTransaction().commit();
    }

    public T findPage(int id) {
        var entity = em.find(classType, id);
        em.detach(entity);
        return entity;
    }

    private EntityManager getEntityManager() {
        if (em == null) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.hibernate_orm.cool");
            em = emf.createEntityManager();
        }

        return em;
    }
}
