package org.backender.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static com.mongodb.client.model.Filters.eq;

class PersonTestTest {

    @Test
    void findAddress() {
        ConnectionString string = new ConnectionString("mongodb://admin:admin@localhost:12345");

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(string)
                .build();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase db = mongoClient.getDatabase("test");
            MongoCollection<Document> c = db.getCollection("address");

            var doc = c.find(eq("street", "xenopol"))
                    .first();

            System.out.println(doc);
        }
    }

    @Test
    void savePersonWithAddress() {
        ConnectionString string = new ConnectionString("mongodb://admin:admin@localhost:12345");

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(string)
                .build();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase db = mongoClient.getDatabase("test");
            MongoCollection<Document> c = db.getCollection("address");

            var doc = c.find(eq("street", "xenopol"))
                    .first();


            MongoCollection<Document> p = db.getCollection("person");
            p.insertOne(new Document()
                    .append("address", Collections.singletonList(doc.get("_id")))
                    .append("name", "youtube")
            );

        }
    }

    @Test
    void updateExistingDocument() {

        ConnectionString string = new ConnectionString("mongodb://admin:admin@localhost:12345");

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(string)
                .build();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase db = mongoClient.getDatabase("test");
            MongoCollection<Document> addressCollection = db.getCollection("address");
            Document query = new Document();

            Bson updates = Updates.combine(
                        Updates.set("street", "e"));

            UpdateOptions options = new UpdateOptions().upsert(false);

            UpdateResult result = addressCollection.updateOne(query, updates, options);

            System.out.println("Modified document count: " + result.getModifiedCount());
            System.out.println("Upserted id: " + result.getUpsertedId());
        }

    }
}