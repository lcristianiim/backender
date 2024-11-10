package org.eclipselinkdatacenter.persons;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.interactor.modules.datacenter.AddressDTO;
import org.interactor.modules.datacenter.PersonDTO;
import org.interactor.modules.datacenter.PersonsRepository;

import java.util.List;

public class PersonRepositoryImplementation implements PersonsRepository {
    private static EntityManager em;

    private void savePerson(PersonEntity person) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(person);
        em.getTransaction().commit();
    }

    private static List<PersonEntity> fetchAllPersonsFromDb() {
        EntityManager em = getEntityManager();
        em.clear();

        em.getTransaction().begin();

        String sql = "SELECT p FROM %s p".formatted(PersonEntity.TABLE_NAME);
        TypedQuery<PersonEntity> query = em.createQuery(sql, PersonEntity.class);
        List<PersonEntity> persons = query.getResultList();

        em.getTransaction().commit();

        return persons;
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
        List<PersonEntity> personEntities = fetchAllPersonsFromDb();
        return personEntities.stream()
                .map(e -> new PersonDTO(e.getId(), e.getFirstName(), e.getLastName(),
                        e.getAddresses().stream()
                                .map(adr -> new AddressDTO(adr.getId(), adr.getAddress(), adr.getPostalCode()))
                                .toList()))
                .toList();
    }

    private static EntityManager getEntityManager() {
        if (em == null) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.hibernate.unit");
            em = emf.createEntityManager();
        }

        return em;
    }
}
