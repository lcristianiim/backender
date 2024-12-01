package org.interactor.internals;

import com.fasterxml.jackson.databind.ObjectMapper;

public enum ObjectMapperSingleton {
    INSTANCE;
    private final ObjectMapper objectMapper;

    ObjectMapperSingleton() {
        this.objectMapper = new ObjectMapper();
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
