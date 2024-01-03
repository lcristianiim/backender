package org.interactor.personsa;

import java.util.List;

public interface PersonsRepository {
    void savePerson(PersonDTO person);
    List<PersonDTO> getAllPersons();
}
