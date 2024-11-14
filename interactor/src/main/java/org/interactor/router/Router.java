package org.interactor.router;

import org.interactor.ApplicationConfiguration;
import org.interactor.modules.logging.LoggerService;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

public class Router {

    ControllerResolver<String, RequestContext, Controller> controllerResolver = this::getControllerInstance;

    Function<String,String> pathWithoutTheAPI = PathOperations.getPathWithoutTheAPIPart();
    Properties theGETRoutes = ApplicationConfiguration.INSTANCE.getGETRoutes();
    LoggerService logger =  LoggerService.INSTANCE;
    Class<Router> clazz = Router.class;

    /**
     * This is the main gateway from webserver to the interactor module for all GET requests
     *
     * @param ctx this is the all the request context
     * @return Always ResponseBody is the object that is returned by Controllers by convention
     */
    public ResponseBody get(RequestContext ctx) {
        String pathWithoutAPI = pathWithoutTheAPI.apply(ctx.getRequestPath());
        logger.getLogging().info("Request path:" + ctx.requestPath, clazz);

        for (Map.Entry<Object, Object> routeElement : theGETRoutes.entrySet()) {

            String pathFromConf = (String) routeElement.getKey();
            String controllerName = (String) routeElement.getValue();

            if (pathFromConf.equals(pathWithoutAPI)) {
                Controller controller = controllerResolver.apply(controllerName, new RequestContext());
                return controller.getResponse();
            }
        }

        ResponseBody response = new ResponseBody();
        response.setBody("Path: " + pathWithoutAPI + " is not part of the API");
        response.setCode(500);
        response.setType(ResponseType.JSON);

        return response;
    }

    private Controller getControllerInstance(String className, RequestContext ctx) {
        try {
            Class<?> clazz = null;
            clazz = Class.forName(className);
            return (Controller) clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    };

    public void setControllerResolver(ControllerResolver<String, RequestContext, Controller> controllerResolver) {
        this.controllerResolver = controllerResolver;
    }

    public void setTheGETRoutes(Properties theGETRoutes) {
        this.theGETRoutes = theGETRoutes;
    }
}
