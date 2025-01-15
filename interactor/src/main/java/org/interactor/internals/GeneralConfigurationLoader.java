package org.interactor.internals;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GeneralConfigurationLoader {
    private static final String ENVIRONMENT = "localhost";
    private static final String COMMON_CONFIGURATION = "common";
    private static final String FOLDER_IN_RESOURCES = "environments";

    public Properties loadEnvironmentBasedConfiguration(Class clazz) {
        return loadConfiguration(clazz, ENVIRONMENT);
    }

    public Properties loadCommonConfiguration(Class clazz) {
        return loadConfiguration(clazz, COMMON_CONFIGURATION);
    }

    private Properties loadConfiguration(Class clazz, String propertyFile) {
        Properties result = new Properties();
        String moduleName = fetchJpmsModuleName(clazz);

        try (InputStream input = ModuleLayer.boot().findModule(moduleName).get()
                .getResourceAsStream( moduleName + "/" + FOLDER_IN_RESOURCES + "/" + propertyFile + ".properties")) {

            result.load(input);
            return result;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String fetchJpmsModuleName(Class clazz) {
        Module module = clazz.getModule();
        return module.getName();
    }

    public String loadConfiguration(String config, Properties commonConfiguration, Properties environmentBasedConfiguration) {
        String foundConfiguration = (String) commonConfiguration.get(config);
        if (foundConfiguration == null)
            return (String) environmentBasedConfiguration.get(config);

        return foundConfiguration;
    }
}
