package org.backender.router;

import org.interactor.internals.Route;
import org.interactor.modules.logging.LoggerService;

import org.interactor.modules.router.Router;
import org.interactor.configuration.RegisteredRoute;
import org.interactor.modules.router.dtos.*;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.interactor.configuration.RegisteredRoute.getRoutesByHttpMethod;
import static org.interactor.modules.router.dtos.RequestType.*;

public class RouterImplementation implements Router {

    List<Route> getRoutes = getRoutesByHttpMethod(GET);
    List<Route> postRoutes = getRoutesByHttpMethod(POST);
    List<Route> putRoutes = getRoutesByHttpMethod(PUT);
    List<Route> patchRoutes = getRoutesByHttpMethod(PATCH);
    List<Route> headRoutes = getRoutesByHttpMethod(HEAD);
    List<Route> traceRoutes = getRoutesByHttpMethod(TRACE);
    List<Route> ConnectRoutes = getRoutesByHttpMethod(CONNECT);
    List<Route> optionsRoutes = getRoutesByHttpMethod(OPTIONS);
    List<Route> beforeRoutes = getRoutesByHttpMethod(BEFORE);
    List<Route> beforeMatchedRoutes = getRoutesByHttpMethod(BEFORE_MATCHED);
    List<Route> afterMatchedRoutes = getRoutesByHttpMethod(AFTER_MATCHED);
    List<Route> websocketBeforeUpgrade = getRoutesByHttpMethod(WEBSOCKET_BEFORE_UPGRADE);
    List<Route> websocketAfterUpgrade = getRoutesByHttpMethod(WEBSOCKET_AFTER_UPGRADE);
    List<Route> afterRoutes = getRoutesByHttpMethod(AFTER);
    List<Route> invalidRoutes = getRoutesByHttpMethod(INVALID);
    List<Route> deleteRoutes = getRoutesByHttpMethod(DELETE);

    LoggerService logger =  LoggerService.INSTANCE;
    Class<RouterImplementation> clazz = RouterImplementation.class;

    public InteractorResponse processRequest(InteractorRequest ctx) {
        logger.getLogging().info("Process request:" + ctx.getRequestPath(), clazz);

        Optional<Route> entry =
                getRegisteredRouteAsEntry(ctx.getRequestPath(), getRoutesByHttpMethod(ctx.getRequestType()));

        if (entry.isPresent()) {
            entry.get().controller().initialize(ctx, entry.get());

            return entry.get().controller().getResponse();
        }

        return invalidRequestResponse(ctx.getRequestPath());
    }

    private List<Route> getRoutesByRequestType(RequestType requestType) {
        return switch (requestType) {
            case GET -> getRoutes;
            case POST -> postRoutes;
            case PUT -> putRoutes;
            case PATCH -> patchRoutes;
            case HEAD -> headRoutes;
            case TRACE -> traceRoutes;
            case CONNECT -> ConnectRoutes;
            case OPTIONS -> optionsRoutes;
            case BEFORE -> beforeRoutes;
            case BEFORE_MATCHED -> beforeMatchedRoutes;
            case AFTER_MATCHED -> afterMatchedRoutes;
            case WEBSOCKET_BEFORE_UPGRADE -> websocketBeforeUpgrade;
            case WEBSOCKET_AFTER_UPGRADE -> websocketAfterUpgrade;
            case AFTER -> afterRoutes;
            case INVALID -> invalidRoutes;
            case DELETE -> deleteRoutes;
        };
    }

    @Override
    public Map<String, String> getPathParams(String registeredPath, String requestPath) {
        PathCommonOperations operations = new PathCommonOperations();
//        String apiPath = ApplicationConfiguration.INSTANCE.getApiPath();
//        String pathWithoutApi = requestPath.split(apiPath + "/")[1];

        return operations.getPathParams(
                registeredPath, requestPath);
    }

    @Override
    public Map<String, String> getQueryParams(String registeredPath, String requestPath) {
        PathCommonOperations operations = new PathCommonOperations();
//        String apiPath = ApplicationConfiguration.INSTANCE.getApiPath();
//        String pathWithoutApi = requestPath.split(apiPath + "/")[1];

        return operations.getQueryParams(
                registeredPath, requestPath);
    }

    private static InteractorResponse invalidRequestResponse(String pathWithoutAPI) {
        InteractorResponse response = new InteractorResponse();
        response.setBody("Path: " + pathWithoutAPI + " is not part of the API");
        response.setCode(404);
        return response;
    }

    private Optional<Route> getRegisteredRouteAsEntry(String pathWithoutAPI, List<Route> routes) {

        for (Route registeredRoute : routes) {

            String registeredPath = registeredRoute.path();

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

}
