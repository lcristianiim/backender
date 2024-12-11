package org.backender.interactorimplementations;

import org.bson.types.ObjectId;
import org.interactor.modules.datacenter.dtos.AddressDTO;
import org.interactor.modules.datacenter.dtos.PersonDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonsRepositoryTest {


    @Test
    void a() {
        PersonDTO person = PersonsRepository.INSTANCE.findById(new ObjectId("6759211221176c50546655de"));
        assertNotNull(person);
    }

    @Test
    void b() {
        List<PersonDTO> persons = PersonsRepository.INSTANCE.findAll();
        assertFalse(persons.isEmpty());
    }

    @Test
    void saveNonExistingRecord() {
        List<AddressDTO> addresses = List.of(
                new AddressDTO(0, "mata", 123),
                new AddressDTO(1, "hell0", 123));

        PersonDTO personDTO = new PersonDTO(
                null,
                "John",
                "Doe",
                addresses

        );
        PersonsRepository.INSTANCE.save(personDTO);
    }

    @Test
    void updateExisting() {
        List<AddressDTO> addresses = List.of(
                new AddressDTO(0, "tactau", 123),
                new AddressDTO(1, "hell0", 123));

        PersonDTO personDTO = new PersonDTO(
                "6759211221176c50546655de",
                "Keryy",
                "Doe",
                addresses

        );
        PersonsRepository.INSTANCE.save(personDTO);
    }
}