package org.interactor.configuration;

import org.interactor.internals.InteractorConfigurationLoader;

public enum Configuration {
    API_PATH(fetchByKey("api.path"));

    private static String fetchByKey(String key) {
        return InteractorConfigurationLoader.INSTANCE.loadConfiguration(key);
    }

    private final String value;

    Configuration(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}