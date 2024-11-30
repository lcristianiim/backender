package org.eclipselinkdatacenter.interactorimplementations;

import org.interactor.modules.datacenter.dtos.PersonDTO;
import org.interactor.modules.datacenter.interfaces.PersonsPersistence;

import java.util.List;

public class HardCodedPersonRepositoryImpl implements PersonsPersistence {
    @Override
    public void save(PersonDTO person) {
        System.out.println("hello world");
    }

    @Override
    public List<PersonDTO> findAll() {
//        PersonDTO personDTO = new PersonDTO(0, "John", "Perry", List.of(new AddressDTO(0, "First Street", 1234)));
//        PersonDTO secondPersonDTO = new PersonDTO(1, "Mike", "Adams", List.of(new AddressDTO(0, "Second Street", 1234)));
//        return List.of(personDTO, secondPersonDTO);
        return List.of();
    }
}
