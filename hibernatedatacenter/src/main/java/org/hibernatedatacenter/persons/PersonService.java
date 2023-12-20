package org.hibernatedatacenter.persons;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.interactor.personsa.AddressDTO;
import org.interactor.personsa.PersonDTO;
import org.interactor.personsa.PersonsService;

import java.util.List;

public class PersonService implements PersonsService {
    private static EntityManager em;

    private void savePerson(PersonEntity person) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(person);
        em.getTransaction().commit();
    }

    private static List<PersonEntity> getPersons() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        List<PersonEntity> persons = em.createQuery("SELECT e FROM PersonEntity e").getResultList();
        em.getTransaction().commit();
        return persons;
    }

    private static EntityManager getEntityManager() {
        if (em == null) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.hibernate.unit");
            em = emf.createEntityManager();
        }

        return em;
    }

    @Override
    public void savePerson(PersonDTO person) {
        PersonEntity entity = new PersonEntity();
        entity.setFirstName(person.firstName());
        entity.setLastName(person.lastName());
        List<AddressEntity> addressEntities = person.address().stream()
                .map(e -> {
                    AddressEntity addressEntity = new AddressEntity();
                    addressEntity.setAddress(e.address());
                    addressEntity.setPostalCode(e.postalCode());
                    return addressEntity;
                })
                .toList();

        entity.setAddresses(addressEntities);

        savePerson(entity);
    }

    @Override
    public List<PersonDTO> getAllPersons() {
        List<PersonEntity> personEntities = getPersons();
        return personEntities.stream()
                .map(e -> new PersonDTO(e.getId(), e.getFirstName(), e.getLastName(),
                        e.getAddresses().stream()
                                .map(adr -> new AddressDTO(adr.getId(), adr.getAddress(), adr.getPostalCode()))
                                .toList()))
                .toList();
    }
}
