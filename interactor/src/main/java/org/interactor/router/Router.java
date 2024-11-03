package org.interactor.router;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

public class Router {
    public void get(String path, Locale locale) {
        Properties configuration = loadRouterConfiguration();
    }

    private Properties loadRouterConfiguration() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("routes-conf.properties")) {
            properties.load(input);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
