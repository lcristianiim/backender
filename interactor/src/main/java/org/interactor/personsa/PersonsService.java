package org.interactor.personsa;

import java.util.List;

public interface PersonsService {
    void savePerson(PersonDTO person);
    List<PersonDTO> getAllPersons();
}
