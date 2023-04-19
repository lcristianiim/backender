package org.backender.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.backender.hibernate.PageEntity;
import org.componenter.Page;

//public class PageRepo implements PersistenceRepo<Page> {
//    private EntityManager em;
//
//    private EntityManager getEntityManager() {
//        if (em == null) {
//            EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.hibernate_orm.cool");
//            em = emf.createEntityManager();
//        }
//
//        return em;
//    }
//
//    @Override
//    public void persist(Page page) {
//        EntityManager entityManager = getEntityManager();
//        entityManager.getTransaction().begin();
//        PageEntity pageEntity = PageEntity.dtoToEntity(page);
////        entityManager.persist(pageEntity.getComponents());
//        entityManager.persist(pageEntity);
//        entityManager.getTransaction().commit();
//    }
//
//    public void persist(PageEntity pageEntity) {
//        EntityManager entityManager = getEntityManager();
//        entityManager.getTransaction().begin();
//        entityManager.persist(pageEntity);
//        entityManager.getTransaction().commit();
//    }
//
//}