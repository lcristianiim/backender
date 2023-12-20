package org.interactor.personsa;

import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

public class HibernatePersonService implements PersonsService {
    private static PersonsService personsService;


    public HibernatePersonService() {
        if (personsService == null)
            setProvider();
    }

    @Override
    public List<PersonDTO> getAllPersons() {
        return personsService.getAllPersons();
    }

    @Override
    public void savePerson(PersonDTO person) {
        personsService.savePerson(person);
    }

    private static void setProvider() {
        ServiceLoader<PersonsService> sl
                = ServiceLoader.load(PersonsService.class);
        Iterator<PersonsService> iter = sl.iterator();
        if (!iter.hasNext())
            throw new NoHibernateProviderException();
        personsService = iter.next();
    }


    private static class NoHibernateProviderException extends RuntimeException {
        public NoHibernateProviderException() {
            super();
        }
    }
}
