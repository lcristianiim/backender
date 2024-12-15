package org.backender.router;

import org.interactor.ApplicationConfiguration;
import org.interactor.modules.logging.LoggerService;

import org.interactor.modules.router.Router;
import org.interactor.configuration.RegisteredRoute;
import org.interactor.modules.router.dtos.Controller;
import org.interactor.modules.router.dtos.ReqContextDTO;
import org.interactor.modules.router.dtos.ResponseType;
import org.interactor.modules.router.dtos.InteractorResponse;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;

public class RouterImplementation implements Router {

    ControllerResolver<String, ReqContextDTO, Controller> controllerResolver = this::getControllerInstance;
    Map<String, Controller> getRoutes = RegisteredRoute.getGETRoutes();
    Map<String, Controller> postRoutes = RegisteredRoute.getPOSTRoutes();


    LoggerService logger =  LoggerService.INSTANCE;
    Class<RouterImplementation> clazz = RouterImplementation.class;

    public Optional<RegisteredRoute> getRegisteredRoute(ReqContextDTO ctx) {
        String pathWithoutAPI = PathOperations.getPathWithoutTheAPIPart(ctx.getRequestPath());
        logger.getLogging().info("GET Request path:" + ctx.getRequestPath(), clazz);

        return getRegisteredRoute(pathWithoutAPI, getRoutes);
    }


    public InteractorResponse get(ReqContextDTO ctx) {
        String pathWithoutAPI = PathOperations.getPathWithoutTheAPIPart(ctx.getRequestPath());
        logger.getLogging().info("GET Request path:" + ctx.getRequestPath(), clazz);

        Optional<Map.Entry<String, Controller>> entry = getRegisteredRouteAsEntry(pathWithoutAPI, getRoutes);

        if (entry.isPresent()) {
            String registeredPath = entry.get().getKey();
            Controller controller = entry.get().getValue();
            controller.initialize(ctx, registeredPath);

            return controller.getResponse();
        }

        return invalidRequestResponse(pathWithoutAPI);
    }

    public InteractorResponse post(ReqContextDTO ctx) {
        String pathWithoutAPI = PathOperations.getPathWithoutTheAPIPart(ctx.getRequestPath());
        logger.getLogging().info("POST Request path:" + ctx.getRequestPath(), clazz);

        Optional<Map.Entry<String, Controller>> entry = getRegisteredRouteAsEntry(pathWithoutAPI, postRoutes);

        if (entry.isPresent()) {
            String registeredPath = entry.get().getKey();
            Controller controller = entry.get().getValue();
            controller.initialize(ctx, registeredPath);

            return controller.getResponse();
        }

        return invalidRequestResponse(pathWithoutAPI);
    }

    @Override
    public Map<String, String> getPathParams(String registeredPath, String requestPath) {
        PathCommonOperations operations = new PathCommonOperations();
        String apiPath = ApplicationConfiguration.INSTANCE.getApiPath();
        String pathWithoutApi = requestPath.split(apiPath + "/")[1];

        return operations.getPathParams(
                registeredPath, pathWithoutApi);
    }

    @Override
    public Map<String, String> getQueryParams(String registeredPath, String requestPath) {
        PathCommonOperations operations = new PathCommonOperations();
        String apiPath = ApplicationConfiguration.INSTANCE.getApiPath();
        String pathWithoutApi = requestPath.split(apiPath + "/")[1];

        return operations.getQueryParams(
                registeredPath, pathWithoutApi);
    }

    private static InteractorResponse invalidRequestResponse(String pathWithoutAPI) {
        InteractorResponse response = new InteractorResponse();
        response.setBody("Path: " + pathWithoutAPI + " is not part of the API");
        response.setCode(500);
        response.setType(ResponseType.JSON);
        return response;
    }

    private Optional<Map.Entry<String, Controller>> getRegisteredRouteAsEntry(String pathWithoutAPI, Map<String, Controller> getRoutes) {

        for (Map.Entry<String, Controller> registeredRoute : getRoutes.entrySet()) {

            String registeredPath = registeredRoute.getKey();

            PathCommonOperations operations = new PathCommonOperations();
            if (operations.isARegisteredControllerMatch(registeredPath, pathWithoutAPI))
                return Optional.of(registeredRoute);

        }
        return Optional.empty();
    }


    private Optional<RegisteredRoute> getRegisteredRoute(String pathWithoutAPI, Map<String, Controller> getRoutes) {
        for (RegisteredRoute registeredRoute : RegisteredRoute.values()) {

            String registeredPath = registeredRoute.getPath();

            PathCommonOperations operations = new PathCommonOperations();
            if (operations.isARegisteredControllerMatch(registeredPath, pathWithoutAPI))
                return Optional.of(registeredRoute);

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

    private static class NoControllerRegisteredToRequestedRoute extends RuntimeException {
        public NoControllerRegisteredToRequestedRoute(String message) {
            super(message);
        }
    }

    public void setGetRoutes(Map<String, Controller> getRoutes) {
        this.getRoutes = getRoutes;
    }

    public void setPostRoutes(Map<String, Controller> postRoutes) {
        this.postRoutes = postRoutes;
    }
}
