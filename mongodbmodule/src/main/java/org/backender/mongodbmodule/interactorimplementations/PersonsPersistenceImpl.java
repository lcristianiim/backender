package org.backender.mongodbmodule.interactorimplementations;

import com.mongodb.client.*;
import org.backender.mongodbmodule.internals.MongoReadSingleton;
import org.backender.mongodbmodule.repositories.PersonsRepository;
import org.bson.Document;
import org.interactor.modules.datacenter.dtos.PersonDTO;
import org.interactor.modules.datacenter.interfaces.PersonsPersistence;

import java.util.ArrayList;
import java.util.List;

public class PersonsPersistenceImpl implements PersonsPersistence {

    @Override
    public void save(PersonDTO person) {
        PersonsRepository.INSTANCE.save(person);
    }

    @Override
    public List<PersonDTO> findAll() {
        List<PersonDTO> result = new ArrayList<>();
        List<PersonDocument> personDocuments = new ArrayList<>();

        MongoDatabase database = MongoReadSingleton.INSTANCE.getDatabase();
        MongoCollection<Document> personsCollection = database.getCollection("persons");

        try (MongoCursor<Document> cursor = personsCollection.find().iterator()) {
            while (cursor.hasNext()) {
                result.add(PersonMapper.INSTANCE.toDTO(MapperUtil.fromDocument(cursor.next(), PersonDocument.class)));
            }
        }

        return result;
    }
}
