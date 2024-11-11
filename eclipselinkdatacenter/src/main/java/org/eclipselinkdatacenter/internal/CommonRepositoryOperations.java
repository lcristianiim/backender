package org.eclipselinkdatacenter.internal;

import jakarta.persistence.criteria.CriteriaQuery;

import java.util.List;

public class CommonRepositoryOperations<T> {

    public T findById(Integer id, Class<T> clazz) {
        try (var em = EntityManagerFactorySingleton.INSTANCE.create()) {
            return em.find(clazz, id);
        }
    }

    public List<T> findAll(Class<T> clazz) {
        try (var em = EntityManagerFactorySingleton.INSTANCE.create()) {
            CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(clazz);
            criteriaQuery.from(clazz);
            return em.createQuery(criteriaQuery).getResultList();
        }
    }

    public void save(T entity) {
        try (var em = EntityManagerFactorySingleton.INSTANCE.create()) {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        }
    }

    public void delete(T entity) {
        try (var em = EntityManagerFactorySingleton.INSTANCE.create()) {
            em.getTransaction().begin();
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            em.getTransaction().commit();
        }
    }
}
