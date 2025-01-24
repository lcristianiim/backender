package org.backender.jwtauth.configuration;

import org.interactor.internals.GeneralConfigurationLoader;

import java.util.Properties;

public enum JwtAuthModuleConfigurationLoader {
    INSTANCE;
    private static Properties environmentBasedConfiguration = new Properties();
    private static Properties commonConfiguration = new Properties();
    private static final GeneralConfigurationLoader generalConfigurationLoader = new GeneralConfigurationLoader();

    static {
        environmentBasedConfiguration = generalConfigurationLoader.loadEnvironmentBasedConfiguration(JwtAuthModuleConfigurationLoader.class);
        commonConfiguration =  generalConfigurationLoader.loadCommonConfiguration(JwtAuthModuleConfigurationLoader.class);
    }

    public String loadConfiguration(String config) {
        return generalConfigurationLoader.loadConfiguration(config, commonConfiguration, environmentBasedConfiguration);
    }
}
