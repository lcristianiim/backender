package org.eclipselinkdatacenter.internal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public enum EntityManagerFactorySingleton {
    INSTANCE;

    private final EntityManagerFactory emf;

    EntityManagerFactorySingleton() {
        emf = Persistence.createEntityManagerFactory("org.backender.main-unit");
    }

    public EntityManager create() {
        return emf.createEntityManager();
    }
}
