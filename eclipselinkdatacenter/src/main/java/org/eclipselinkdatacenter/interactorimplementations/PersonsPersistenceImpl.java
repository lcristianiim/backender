package org.eclipselinkdatacenter.interactorimplementations;

import org.eclipselinkdatacenter.entities.PersonEntity;
import org.eclipselinkdatacenter.mappers.PersonMapper;
import org.eclipselinkdatacenter.repositories.PersonRepository;
import org.interactor.modules.datacenter.dtos.PersonDTO;
import org.interactor.modules.datacenter.interfaces.PersonsPersistence;

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