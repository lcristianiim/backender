package org.eclipselinkdatacenter.persons;

import org.eclipselinkdatacenter.internal.CommonRepositoryOperations;
import org.eclipselinkdatacenter.internal.GenericRepository;

import java.util.List;

public enum PersonRepository implements GenericRepository<PersonEntity, Integer> {
    INSTANCE;
    private final Class<PersonEntity> clazz;
    private final CommonRepositoryOperations<PersonEntity> commonOperations = new CommonRepositoryOperations<>();

    PersonRepository() {
        clazz = PersonEntity.class;
    }

    @Override
    public PersonEntity findById(Integer id) {
        return commonOperations.findById(id, clazz);
    }

    @Override
    public List<PersonEntity> findAll() {
        return commonOperations.findAll(clazz);
    }

    @Override
    public void save(PersonEntity entity) {
        commonOperations.save(entity);
    }

    @Override
    public void delete(PersonEntity entity) {
        commonOperations.save(entity);
    }
}