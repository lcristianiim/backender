package org.backender.interactorimplementations;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.connection.ConnectionPoolSettings;

import java.util.concurrent.TimeUnit;

public enum MongoReadSingleton {
    INSTANCE;

    private final ConnectionString connectionString = new ConnectionString("mongodb://admin:admin@localhost:27017/");
    private final ConnectionPoolSettings connectionPoolSettings;
    private final MongoDatabase database;


    MongoReadSingleton() {
        this.connectionPoolSettings = connectionPoolSettings();
        this.database = createDatabase();
    }

    private MongoDatabase createDatabase() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .applyToConnectionPoolSettings(builder -> builder.applySettings(connectionPoolSettings))
                .build();

        MongoClient mongoClient = MongoClients.create(settings);
        return mongoClient.getDatabase("backender");
    }

    private ConnectionPoolSettings connectionPoolSettings() {
        return ConnectionPoolSettings.builder()
                .minSize(5) // Minimum number of connections in the pool
                .maxSize(20) // Maximum number of connections in the pool
                .maxWaitTime(30000, TimeUnit.MILLISECONDS) // Maximum wait time for a connection
                .build();
    }

    public MongoDatabase getDatabase() {
        return database;
    }
}
