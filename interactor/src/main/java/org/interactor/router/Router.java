package org.interactor.router;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.Supplier;

public class Router {

    Function<String, ResponseBody> getResponseBodySideEffect = this::getResponseBody;
    Supplier<Properties> propertiesSideEffects = this::loadRouterConfiguration;

    /**
     * This is the main gateway from webserver to the interactor module
     *
     * @param path this is the path accessed by the user through webserver module
     * @param locale locale decided in the webserver module
     * @return Always ResponseBody is the object that is returned by Controllers by convention
     */
    public ResponseBody get(String path, Locale locale) {
        Properties configuration = propertiesSideEffects.get();

        for (Map.Entry<Object, Object> routeElement : configuration.entrySet()) {
            String key = (String) routeElement.getKey();
            String value = (String) routeElement.getValue();

            if (key.equals(path)) {
                // Create a new instance of the class
                return getResponseBodySideEffect.apply(value);
            }
        }

        return new ResponseBody("Path: " + path + " is not part of the API", 500);
    }

    private ResponseBody getResponseBody(String value) {
        try {
            Class<?> clazz = null;
            clazz = Class.forName(value);
            Controller instance = (Controller) clazz.getDeclaredConstructor().newInstance();
            return instance.getResponse();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    };

    private Properties loadRouterConfiguration() {
        Properties properties = new Properties();
        try (InputStream input = this.getClass().getClassLoader().getResourceAsStream("routes-conf.properties")) {
            properties.load(input);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void setGetResponseBodySideEffect(Function<String, ResponseBody> getResponseBodySideEffect) {
        this.getResponseBodySideEffect = getResponseBodySideEffect;
    }

    void setPropertiesSideEffects(Supplier<Properties> propertiesSideEffects) {
        this.propertiesSideEffects = propertiesSideEffects;
    }
}
