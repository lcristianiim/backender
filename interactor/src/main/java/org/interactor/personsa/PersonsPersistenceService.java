package org.interactor.personsa;

import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;

public class PersonsPersistenceService {
    private static PersonsRepository personsRepository;

    public PersonsPersistenceService() {
        if (personsRepository == null)
            setProvider();
    }

    public List<PersonDTO> getAllPersons() {
        return personsRepository.getAllPersons();
    }

    public void savePerson(PersonDTO person) {
        personsRepository.savePerson(person);
    }

    private static void setProvider() {
        ServiceLoader<PersonsRepository> sl
                = ServiceLoader.load(PersonsRepository.class);
        Optional<PersonsRepository> repositoryImplementation = sl.findFirst();
        if (repositoryImplementation.isEmpty())
            throw new NoHibernateProviderException();
        personsRepository = repositoryImplementation.get();
    }


    private static class NoHibernateProviderException extends RuntimeException {
        public NoHibernateProviderException() {
            super();
        }
    }
}
