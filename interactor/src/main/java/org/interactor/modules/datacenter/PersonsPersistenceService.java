package org.interactor.modules.datacenter;

import org.interactor.modules.ModuleImplementationLoader;
import org.interactor.modules.datacenter.dtos.PersonDTO;
import org.interactor.modules.datacenter.interfaces.PersonsPersistence;

import java.util.List;

public enum PersonsPersistenceService {
    INSTANCE;

    private final PersonsPersistence personsRepository;

    PersonsPersistenceService() {
        personsRepository = ModuleImplementationLoader.load(PersonsPersistence.class);
    }

    public List<PersonDTO> getAllPersons() {
        return personsRepository.findAll();
    }

    public void savePerson(PersonDTO person) {
        personsRepository.save(person);
    }
}
