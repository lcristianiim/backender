package org.backender.mongodbmodule.configuration;

import org.interactor.internals.GeneralConfigurationLoader;

import java.util.Properties;

public enum MongoDbModuleConfigurationLoader {
    INSTANCE;
    private static Properties environmentBasedConfiguration = new Properties();
    private static Properties commonConfiguration = new Properties();
    private static final GeneralConfigurationLoader generalConfigurationLoader = new GeneralConfigurationLoader();

    static {
        environmentBasedConfiguration = generalConfigurationLoader.loadEnvironmentBasedConfiguration(MongoDbModuleConfigurationLoader.class);
        commonConfiguration =  generalConfigurationLoader.loadCommonConfiguration(MongoDbModuleConfigurationLoader.class);
    }

    public String loadConfiguration(String config) {
        return generalConfigurationLoader.loadConfiguration(config, commonConfiguration, environmentBasedConfiguration);
    }
}
