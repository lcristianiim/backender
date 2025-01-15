package org.backender.mongodbmodule.configuration;

public enum Configuration {
    DATABASE_CONNECTION(fetchByKey("database.connection")),
    DATABASE_NAME(fetchByKey("database.name")),
    CONNECTION_POOL_MIN_SIZE(fetchByKey("connection.pool.min.size")),
    CONNECTION_POOL_MAX_SIZE(fetchByKey("connection.pool.max.size")),
    CONNECTION_POOL_WAIT_TIME(fetchByKey("connection.pool.max.wait.time"));

    private static String fetchByKey(String key) {
        return MongoDbModuleConfigurationLoader.INSTANCE.loadConfiguration(key);
    }

    private final String value;

    Configuration(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
