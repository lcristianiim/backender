package org.backender.mongodbmodule.interactorimplementations;

import org.backender.mongodbmodule.repositories.PersonsRepository;
import org.bson.types.ObjectId;
import org.interactor.modules.datacenter.dtos.AddressDTO;
import org.interactor.modules.datacenter.dtos.PersonDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PersonsRepositoryTest {


    @Test
    @Disabled
    void findById() {
        PersonDTO person = PersonsRepository.INSTANCE.findById(new ObjectId("6759211221176c50546655de"));
        assertNotNull(person);
    }

    @Test
    @Disabled
    void findAll() {
        List<PersonDTO> persons = PersonsRepository.INSTANCE.findAll();
        assertFalse(persons.isEmpty());
    }

    @Test
    @Disabled
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
    @Disabled
    void updateExisting() {
        List<AddressDTO> addresses = List.of(
                new AddressDTO(0, "tactau", 123),
                new AddressDTO(1, "hell0", 123));

        PersonDTO personDTO = new PersonDTO(
                "6759211221176c50546655de",
                "Keryi",
                "Doe",
                addresses

        );
        PersonsRepository.INSTANCE.save(personDTO);
    }

    @Test
    @Disabled
    void delete() {
        Map<String, String> fields = new HashMap<>();
        fields.put("firstName", "Hondaara");
        PersonsRepository.INSTANCE.delete(fields);
    }
}