package org.backender.mongodbmodule.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.backender.mongodbmodule.interactorimplementations.GenericRepository;
import org.backender.mongodbmodule.interactorimplementations.MapperUtil;
import org.backender.mongodbmodule.internals.MongoReadSingleton;
import org.backender.mongodbmodule.internals.MongoWriteSingleton;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.interactor.modules.datacenter.dtos.PersonDTO;

import java.util.List;
import java.util.Map;

public enum PersonsRepository implements GenericRepository<PersonDTO, ObjectId> {
    INSTANCE;
    private static final String COLLECTION_NAME = "persons";
    private final MongoCollection<Document> readCollection;
    private final MongoCollection<Document> writeCollection;

    PersonsRepository() {
        this.readCollection = MongoReadSingleton.INSTANCE.getDatabase().getCollection(COLLECTION_NAME);
        this.writeCollection = MongoWriteSingleton.INSTANCE.getDatabase().getCollection(COLLECTION_NAME);
    }

    public PersonDTO findById(ObjectId objectId) {
        return this.findById(objectId, PersonDTO.class, readCollection);
    }

    public List<PersonDTO> findAll() {
        return this.findAll(PersonDTO.class, readCollection);
    }

    @Override
    public void save(PersonDTO dto) {
        Document doc = MapperUtil.toDocument(dto);
        if (dto.getId() == null) {
            doc.remove("id");
            writeCollection.insertOne(doc);
        } else {
            ObjectId objectId = new ObjectId(dto.getId());
            doc.put("_id", objectId);
            doc.remove("id");
            writeCollection.replaceOne(Filters.eq("_id", objectId), doc);
        }
    }

    public void delete(Map<String, String> fields) {
        this.delete(fields, writeCollection);
    }

}
