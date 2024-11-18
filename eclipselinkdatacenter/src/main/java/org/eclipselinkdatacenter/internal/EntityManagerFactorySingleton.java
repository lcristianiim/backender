package org.eclipselinkdatacenter.internal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public enum EntityManagerFactorySingleton {
    INSTANCE;

    private final EntityManagerFactory emf;

    EntityManagerFactorySingleton() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("jakarta.persistence.jdbc.url", Hikari.INSTANCE.getDataSource().getJdbcUrl());
        properties.put("jakarta.persistence.jdbc.user", Hikari.INSTANCE.getDataSource().getUsername());
        properties.put("jakarta.persistence.jdbc.password", Hikari.INSTANCE.getDataSource().getPassword());
        properties.put("jakarta.persistence.jdbc.driver", "org.postgresql.Driver");
        properties.put("eclipselink.connection.pool.default", Hikari.INSTANCE.getDataSource());

        emf = Persistence.createEntityManagerFactory("org.backender.main-unit", properties);
    }

    public EntityManager create() {
        return emf.createEntityManager();
    }
}
