package org.eclipselinkdatacenter.persons;

import org.interactor.modules.datacenter.PersonDTO;
import org.interactor.modules.datacenter.PersonsPersistence;

import java.util.List;

public class PersonsPersistenceImpl implements PersonsPersistence {

    @Override
    public void save(PersonDTO person) {
        PersonEntity personEntity = PersonMapper.INSTANCE.toEntity(person);
        PersonRepository.INSTANCE.save(personEntity);
    }

    @Override
    public List<PersonDTO> findAll() {
        List<PersonEntity> personEntities = PersonRepository.INSTANCE.findAll();

        return personEntities.stream()
                .map(PersonMapper.INSTANCE::toDTO)
                .toList();
    }
}