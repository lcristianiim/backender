package org.hibernatedatacenter.persons;

import org.interactor.personsa.AddressDTO;
import org.interactor.personsa.PersonDTO;
import org.interactor.personsa.PersonsRepository;

import java.util.List;

public class HardCodedPersonRepositoryImpl implements PersonsRepository {
    @Override
    public void savePerson(PersonDTO person) {
        System.out.println("hello world");
    }

    @Override
    public List<PersonDTO> getAllPersons() {
        PersonDTO personDTO = new PersonDTO(0, "John", "Perry", List.of(new AddressDTO(0, "First Street", 1234)));
        PersonDTO secondPersonDTO = new PersonDTO(1, "Mike", "Adams", List.of(new AddressDTO(0, "Second Street", 1234)));
        return List.of(personDTO, secondPersonDTO);
    }
}
