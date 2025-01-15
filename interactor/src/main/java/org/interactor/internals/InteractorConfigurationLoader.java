package org.interactor.internals;

import java.util.Properties;

public enum InteractorConfigurationLoader {
    INSTANCE;
    private static Properties environmentBasedConfiguration = new Properties();
    private static Properties commonConfiguration = new Properties();
    private static final GeneralConfigurationLoader generalConfigurationLoader = new GeneralConfigurationLoader();

    static {
        environmentBasedConfiguration = generalConfigurationLoader.loadEnvironmentBasedConfiguration(InteractorConfigurationLoader.class);
        commonConfiguration =  generalConfigurationLoader.loadCommonConfiguration(InteractorConfigurationLoader.class);
    }

    public String loadConfiguration(String config) {
        return generalConfigurationLoader.loadConfiguration(config, commonConfiguration, environmentBasedConfiguration);
    }
}
