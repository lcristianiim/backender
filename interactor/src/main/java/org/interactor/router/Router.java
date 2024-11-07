package org.interactor.router;

import org.interactor.ApplicationConfigurationSingleton;
import org.interactor.modules.logging.LoggerServiceSingleton;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

public class Router {

    Function<String, Controller> controllerResolver = this::getControllerInstance;
    Function<String,String> pathWithoutTheAPI = PathOperations.getPathWithoutTheAPIPart();
    Properties theGETRoutes = ApplicationConfigurationSingleton.INSTANCE.getGETRoutes();
    LoggerServiceSingleton logger =  LoggerServiceSingleton.INSTANCE;

    /**
     * This is the main gateway from webserver to the interactor module for all GET requests
     *
     * @param requestPath this is the requestPath accessed by the user through webserver module and it does include the api prefix
     * @param locale locale decided in the webserver module
     * @return Always ResponseBody is the object that is returned by Controllers by convention
     */
    public ResponseBody get(String requestPath, Locale locale) {
        String pathWithoutAPI = pathWithoutTheAPI.apply(requestPath);
        logger.getLogging().info("Request path:" + requestPath, Router.class);

        for (Map.Entry<Object, Object> routeElement : theGETRoutes.entrySet()) {

            String key = (String) routeElement.getKey();
            String value = (String) routeElement.getValue();

            if (key.equals(pathWithoutAPI)) {
                Controller controller = controllerResolver.apply(value);
                return controller.getResponse();
            }
        }

        return new ResponseBody("Path: " + pathWithoutAPI + " is not part of the API", 500);
    }


    private Controller getControllerInstance(String value) {
        try {
            Class<?> clazz = null;
            clazz = Class.forName(value);
            return (Controller) clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    };

    public void setControllerResolver(Function<String, Controller> controllerResolver) {
        this.controllerResolver = controllerResolver;
    }

    public void setTheGETRoutes(Properties theGETRoutes) {
        this.theGETRoutes = theGETRoutes;
    }
}
