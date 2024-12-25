package org.backender.router;

import org.interactor.ApplicationConfiguration;
import org.interactor.configuration.Route;
import org.interactor.modules.logging.LoggerService;

import org.interactor.modules.router.Router;
import org.interactor.configuration.RegisteredRoute;
import org.interactor.modules.router.dtos.*;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class RouterImplementation implements Router {

    ControllerResolver<String, InteractorRequest, Controller> controllerResolver = this::getControllerInstance;
    Map<String, Controller> getRoutes = RegisteredRoute.getGETRoutes();
    Map<String, Controller> postRoutes = RegisteredRoute.getPOSTRoutes();


    LoggerService logger =  LoggerService.INSTANCE;
    Class<RouterImplementation> clazz = RouterImplementation.class;


    public InteractorResponse processRequest(InteractorRequest ctx) {
        logger.getLogging().info("Process request:" + ctx.getRequestPath(), clazz);

        Optional<Map.Entry<String, Controller>> entry =
                getRegisteredRouteAsEntry(ctx.getRequestPath(), getRoutesByRequestType(ctx.getRequestType()));

        if (entry.isPresent()) {
            String registeredPath = entry.get().getKey();
            Controller controller = entry.get().getValue();
            controller.initialize(ctx, registeredPath);

            return controller.getResponse();
        }

        return invalidRequestResponse(ctx.getRequestPath());
    }

    private Map<String, Controller> getRoutesByRequestType(RequestType requestType) {
        return switch (requestType) {
            case GET -> getRoutes;
            case POST -> postRoutes;
            case PUT -> null;
            case PATCH -> null;
            case HEAD -> null;
            case TRACE -> null;
            case CONNECT -> null;
            case OPTIONS -> null;
            case BEFORE -> null;
            case BEFORE_MATCHED -> null;
            case AFTER_MATCHED -> null;
            case WEBSOCKET_BEFORE_UPGRADE -> null;
            case WEBSOCKET_AFTER_UPGRADE -> null;
            case AFTER -> null;
            case INVALID -> null;
            case DELETE -> null;
        };
    }

    public InteractorResponse post(InteractorRequest ctx) {
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
        response.setCode(404);
        response.setType(ResponseType.JSON);
        return response;
    }

    private Optional<Map.Entry<String, Controller>> getRegisteredRouteAsEntry(String pathWithoutAPI, Map<String, Controller> routes) {

        for (Map.Entry<String, Controller> registeredRoute : routes.entrySet()) {

            String registeredPath = registeredRoute.getKey();

            PathCommonOperations operations = new PathCommonOperations();
            if (operations.isARegisteredControllerMatch(registeredPath, pathWithoutAPI))
                return Optional.of(registeredRoute);

        }
        return Optional.empty();
    }


    public Optional<Route> getRegisteredRoute(String path, RequestType requestType) {
        PathCommonOperations operations = new PathCommonOperations();

            return RegisteredRoute.getRegisteredRoutes().stream()
                    .filter(e -> e.requestType().equals(requestType))
                    .filter(e -> operations.isARegisteredControllerMatch(e.path(), path))
                    .findFirst();
    }

    private Controller getControllerInstance(String className, InteractorRequest ctx) {
        try {
            Class<?> clazz = null;
            clazz = Class.forName(className);
            return (Controller) clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void setGetRoutes(Map<String, Controller> getRoutes) {
        this.getRoutes = getRoutes;
    }

    public void setPostRoutes(Map<String, Controller> postRoutes) {
        this.postRoutes = postRoutes;
    }
}
