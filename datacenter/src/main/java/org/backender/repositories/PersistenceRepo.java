package org.backender.repositories;

public interface PersistenceRepo<T> {
    void persist(T object);
}
