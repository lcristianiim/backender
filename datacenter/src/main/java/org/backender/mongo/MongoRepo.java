package org.backender.mongo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;

import java.util.Optional;

import static org.backender.mongo.MongoSettingsFactory.createSettings;

public class MongoRepo<T> {
    public static final String BACKENDER = "test";
    private final Class<T> typeParameterClass;
    private String collection;

    public MongoRepo(Class<T> typeParameterClass, String collection) {
        this.typeParameterClass = typeParameterClass;
        this.collection = collection;
    }

    public MongoCollection<T> getSpecificCollection() {
        try (MongoClient mongoClient = MongoClients.create(createSettings())) {
            MongoDatabase db = mongoClient.getDatabase(BACKENDER);
            MongoCollection<T> collection = db.getCollection(this.collection, typeParameterClass);

            return collection;
        }
    }

    public static MongoCollection getCollection(String collection) {
        try (MongoClient mongoClient = MongoClients.create(createSettings())) {
            MongoDatabase db = mongoClient.getDatabase(BACKENDER);
            MongoCollection<Document> c = db.getCollection(collection);

            return c;
        }
    }

    public boolean insertOne(T object) {
        try (MongoClient mongoClient = MongoClients.create(createSettings())) {
            MongoDatabase db = mongoClient.getDatabase(BACKENDER);
            MongoCollection<T> pagesCollection = db.getCollection(collection, typeParameterClass);

            var insert = pagesCollection.insertOne(object);

            return insert.wasAcknowledged();
        }
    }

    public Optional<T> findOne(String key, String value) {
        try (MongoClient mongoClient = MongoClients.create(createSettings())) {
            MongoDatabase db = mongoClient.getDatabase(BACKENDER);
            MongoCollection<Document> pagesCollection = db.getCollection(collection);

            Document searchQuery = new Document();
            searchQuery.put(key, value);

            FindIterable<Document> cursor = pagesCollection.find(searchQuery);

            try (final MongoCursor<Document> cursorIterator = cursor.cursor()) {
                if (cursorIterator.hasNext()) {
                    var document = cursorIterator.next();
                    var json = document.toJson(JsonWriterSettings
                            .builder()
                            .outputMode(JsonMode.RELAXED)
                            .build());

                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        return Optional.of(mapper.readValue(json, typeParameterClass));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return Optional.empty();
    }

}
