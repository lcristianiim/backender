package org.backender.interactorimplementations;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.interactor.modules.datacenter.dtos.PersonDTO;

import java.util.ArrayList;
import java.util.List;

public enum PersonsRepository implements GenericRepository<PersonDTO, ObjectId> {
    INSTANCE;
    private static final String collectionName = "persons";
    private final MongoCollection<Document> readCollection;
    private final MongoCollection<Document> writeCollection;

    PersonsRepository() {
        this.readCollection = MongoReadSingleton.INSTANCE.getDatabase().getCollection(collectionName);
        this.writeCollection = MongoWriteSingleton.INSTANCE.getDatabase().getCollection(collectionName);
    }

    @Override
    public PersonDTO findById(ObjectId objectId) {
        Document doc = readCollection.find(Filters.eq("_id", objectId)).first();
        if (doc != null)
            return MapperUtil.fromDocument(doc, PersonDTO.class);

        return null;
    }

    @Override
    public List<PersonDTO> findAll() {
        List<PersonDTO> persons = new ArrayList<>();
        for (Document doc : readCollection.find()) {
            persons.add(MapperUtil.fromDocument(doc, PersonDTO.class));
        }
        return persons;
    }

    @Override
    public void save(PersonDTO document) {
        if (document.getId() == null) {
            Document doc = MapperUtil.toDocument(document);
            doc.remove("id");

            writeCollection.insertOne(doc);
            ObjectId userId = doc.getObjectId("_id");
        } else {
            Document doc = MapperUtil.toDocument(document);
            ObjectId objectId = new ObjectId(document.getId());
            doc.put("_id", objectId);
            doc.remove("id");
            writeCollection.replaceOne(Filters.eq("_id", objectId), doc);
        }
    }

    @Override
    public void delete(PersonDTO entity) {

    }

}
