package org.backender.mongodbmodule.internals;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.connection.ConnectionPoolSettings;

import java.util.concurrent.TimeUnit;

import static org.backender.mongodbmodule.configuration.Configuration.*;

public enum MongoWriteSingleton {
    INSTANCE;

    private final ConnectionString connectionString = new ConnectionString(DATABASE_CONNECTION.getValue());
    private final ConnectionPoolSettings connectionPoolSettings;
    private final MongoDatabase database;

    MongoWriteSingleton() {
        this.connectionPoolSettings = connectionPoolSettings();
        this.database = createDatabase(DATABASE_NAME.getValue());
    }

    private MongoDatabase createDatabase(String backender) {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .applyToConnectionPoolSettings(builder -> builder.applySettings(connectionPoolSettings))
                .build();

        MongoClient mongoClient = MongoClients.create(settings);
        return mongoClient.getDatabase(backender);
    }

    private ConnectionPoolSettings connectionPoolSettings() {
        return ConnectionPoolSettings.builder()
                .minSize(Integer.parseInt(CONNECTION_POOL_MIN_SIZE.getValue())) // Minimum number of connections in the pool
                .maxSize(Integer.parseInt(CONNECTION_POOL_MAX_SIZE.getValue())) // Maximum number of connections in the pool
                .maxWaitTime(Integer.parseInt(CONNECTION_POOL_WAIT_TIME.getValue()), TimeUnit.MILLISECONDS) // Maximum wait time for a connection
                .build();
    }

    public MongoDatabase getDatabase() {
        return database;
    }
}
