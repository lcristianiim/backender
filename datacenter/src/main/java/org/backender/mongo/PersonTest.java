package org.backender.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

public class PersonTest {
    public static void main(String[] args) {
//        Address address = new Address("xenopol");
//        Person person = new Person("Cristian", address);

//        MongoRepo<Address> addressRepo = new MongoRepo<Address>(Address.class, "address");
//        var addressCollections = addressRepo.getSpecificCollection();
//        addressRepo.insertOne(address);

//        MongoRepo<Person> personRepo = new MongoRepo<>(Person.class, "person");
//        var personsCollection = personRepo.getSpecificCollection();

        Bson projectionFields = Projections.fields(
                Projections.include("street"),
                Projections.excludeId());



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

//        personRepo.insertOne(person);
    }
}
