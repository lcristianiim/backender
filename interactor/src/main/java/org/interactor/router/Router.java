package org.interactor.router;

import org.interactor.ApplicationConfiguration;
import org.interactor.modules.logging.LoggerService;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Function;

public class Router {

    ControllerResolver<String, ReqContextDTO, Controller> controllerResolver = this::getControllerInstance;

    Function<String,String> pathWithoutTheAPI = PathOperations.getPathWithoutTheAPIPart();
    Properties theGETRoutes = ApplicationConfiguration.INSTANCE.getGETRoutes();
    Properties thePOSTRoutes = ApplicationConfiguration.INSTANCE.getPOSTRoutes();
    LoggerService logger =  LoggerService.INSTANCE;
    Class<Router> clazz = Router.class;

    /**
     * This is the link from webserver to the interactor module for all GET requests
     * <p>
     * the controllerResolver decides what controller to use and then returns the response
     * @param ctx this is the all the request context
     * @return Always ResponseBody is the object that is returned by Controllers by convention
     *
     */
    public RouterResponse get(ReqContextDTO ctx) {
        String pathWithoutAPI = pathWithoutTheAPI.apply(ctx.getRequestPath());
        logger.getLogging().info("GET Request path:" + ctx.requestPath, clazz);

        Optional<String> registeredEntry = getControllerClassNeededForInstantiation(pathWithoutAPI, theGETRoutes);

        if (registeredEntry.isPresent()) {
            String registeredPath = registeredEntry.get().split(":")[0];
            String controllerName = registeredEntry.get().split(":")[1];

            Controller controller = controllerResolver.apply(controllerName, new ReqContextDTO());
            controller.initialize(ctx, registeredPath);

            return controller.getResponse();
        }

        return invalidRequestResponse(pathWithoutAPI);
    }

    /**
     * This is the link from webserver to the interactor module for all POST requests
     * <p>
     * the controllerResolver decides what controller to use and then returns the response
     * @param ctx this is the all the request context
     * @return Always ResponseBody is the object that is returned by Controllers by convention
     *
     */
    public RouterResponse post(ReqContextDTO ctx) {
        String pathWithoutAPI = pathWithoutTheAPI.apply(ctx.getRequestPath());
        logger.getLogging().info("POST Request path:" + ctx.requestPath, clazz);

        Optional<String> registeredEntry = getControllerClassNeededForInstantiation(pathWithoutAPI, thePOSTRoutes);

        if (registeredEntry.isPresent()) {
            String registeredPath = registeredEntry.get().split(":")[0];
            String controllerName = registeredEntry.get().split(":")[1];

            Controller controller = controllerResolver.apply(controllerName, new ReqContextDTO());
            controller.initialize(ctx, registeredPath);

            return controller.getResponse();
        }

        return invalidRequestResponse(pathWithoutAPI);
    }

    private static RouterResponse invalidRequestResponse(String pathWithoutAPI) {
        RouterResponse response = new RouterResponse();
        response.setBody("Path: " + pathWithoutAPI + " is not part of the API");
        response.setCode(500);
        response.setType(ResponseType.JSON);
        return response;
    }

    private Optional<String> getControllerClassNeededForInstantiation(String pathWithoutAPI, Properties theGETRoutes) {
        for (Map.Entry<Object, Object> routeElement : theGETRoutes.entrySet()) {

            String registeredPath = (String) routeElement.getKey();
            String controllerName = (String) routeElement.getValue();

            PathCommonOperations operations = new PathCommonOperations();
            if (operations.isARegisteredControllerMatch(registeredPath, pathWithoutAPI))
                return Optional.of(registeredPath + ":" + controllerName);

        }
        return Optional.empty();
    }

    private Controller getControllerInstance(String className, ReqContextDTO ctx) {
        try {
            Class<?> clazz = null;
            clazz = Class.forName(className);
            return (Controller) clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    };

    public void setControllerResolver(ControllerResolver<String, ReqContextDTO, Controller> controllerResolver) {
        this.controllerResolver = controllerResolver;
    }

    public void setTheGETRoutes(Properties theGETRoutes) {
        this.theGETRoutes = theGETRoutes;
    }

    private static class NoControllerRegisteredToRequestedRoute extends RuntimeException {
        public NoControllerRegisteredToRequestedRoute(String message) {
            super(message);
        }
    }
}
