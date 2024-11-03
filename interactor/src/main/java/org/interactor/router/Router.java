package org.interactor.router;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class Router {

    /**
     * This is the main gateway from webserver to the interactor module
     *
     * @param path this is the path accessed by the user through webserver module
     * @param locale locale decided in the webserver module
     */
    public void get(String path, Locale locale) {
        Properties configuration = loadRouterConfiguration();

        Iterator<Map.Entry<Object, Object>> arrays = configuration.entrySet().iterator();
        while (arrays.hasNext()) {
            Map.Entry<Object, Object> me = arrays.next();
            Object value = me.getValue();
            Object you = me.getKey();
            System.out.println("hello");
        }


    }

    private Properties loadRouterConfiguration() {
        Properties properties = new Properties();
        try (InputStream input = this.getClass().getClassLoader().getResourceAsStream("routes-conf.properties")) {
            properties.load(input);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
