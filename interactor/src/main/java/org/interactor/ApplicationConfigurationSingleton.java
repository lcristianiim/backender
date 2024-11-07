package org.interactor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum ApplicationConfigurationSingleton {
    INSTANCE;
    private final String API_PATH;
    private final Properties config = new Properties();
    private final Properties getRoutes = new Properties();

    ApplicationConfigurationSingleton() {
        loadConfig();
        API_PATH = (String) config.get("api.path");
    }

    private void loadConfig() {
        try (InputStream input = this.getClass().getClassLoader().getResourceAsStream("config.properties")) {
            config.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadGetRequestsConfig() {
        try (InputStream input = this.getClass().getClassLoader().getResourceAsStream("get-routes.properties")) {
            getRoutes.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getApiPath() {
        return API_PATH;
    }

    public Properties getGETRoutes() {
        return getRoutes;
    }

}
